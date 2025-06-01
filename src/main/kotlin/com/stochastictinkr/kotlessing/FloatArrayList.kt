package com.stochastictinkr.kotlessing

import java.nio.FloatBuffer

/**
 * An array list for storing `Float` values. The interface is similar to `ArrayList<Float>`, but it is optimized for
 * performance and memory usage when dealing with large arrays of floats.
 */
class FloatArrayList : Iterable<Float> {
    private var data = FloatArray(0)
    private var modCount = 0

    var size = 0
        private set

    val isEmpty: Boolean get() = size == 0
    val isNotEmpty: Boolean get() = !isEmpty
    val indices: IntRange get() = 0 until size
    private val capacity: Int get() = data.size

    fun add(value: Float, index: Int = size) {
        makeRoomAt(index, 1)
        data[index] = value
        ++size
        modCount++
    }

    fun addAll(vararg values: Float, index: Int = size) {
        makeRoomAt(index, values.size)
        System.arraycopy(values, 0, data, index, values.size)
        size += values.size
        modCount++
    }

    fun addAll(values: FloatArrayList) {
        if (values.isEmpty) return
        makeRoomAt(size, values.size)
        System.arraycopy(values.data, 0, data, size, values.size)
        size += values.size
        modCount++
    }

    fun removeAt(index: Int, count: Int = 1) {
        if (count == 0) {
            return
        }
        require(count >= 1) { "Count must be at least 1" }
        checkBounds(index)
        if (index + count > size) {
            throw IndexOutOfBoundsException("Index: $index, Count: $count, Size: $size")
        }
        System.arraycopy(data, index + count, data, index, size - (index + count))
        size -= count
        modCount++
    }

    private fun makeRoomAt(index: Int, count: Int) {
        checkBounds(index)

        if (size + count > capacity) {
            // Find the next power of two that is greater than or equal to size + count
            val newCapacity = Integer.highestOneBit(size + count).shl(1).coerceAtLeast(MINIMUM_CAPACITY)
            if (index >= size) {
                data = data.copyOf(newCapacity)
            } else {
                val newData = FloatArray(newCapacity)
                System.arraycopy(data, 0, newData, 0, index)
                System.arraycopy(data, index, newData, index + count, size - index)
                data = newData
            }
        } else {
            System.arraycopy(data, index, data, index + count, size - index)
        }
    }

    operator fun get(index: Int): Float {
        checkBounds(index)
        return data[index]
    }

    operator fun set(index: Int, value: Float) {
        checkBounds(index)
        data[index] = value
    }

    private fun checkBounds(index: Int) {
        if (index !in indices) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
    }

    companion object {
        private const val MINIMUM_CAPACITY = 16
    }

    override fun toString(): String {
        return "FloatArrayList(size=$size, data=${data.joinToString(", ")})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FloatArrayList) return false
        if (size != other.size) return false
        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentHashCode() + size * 31
    }

    override fun iterator(): MutableIterator<Float> {
        return object : MutableIterator<Float> {
            private var index = 0
            private var expectedModCount = modCount

            override fun hasNext(): Boolean {
                if (expectedModCount != modCount) {
                    throw ConcurrentModificationException("The list was modified during iteration")
                }
                return index < size
            }

            override fun next(): Float {
                if (!hasNext()) {
                    throw NoSuchElementException("No more elements in the list")
                }
                return data[index++]
            }

            override fun remove() {
                if (expectedModCount != modCount) {
                    throw ConcurrentModificationException("The list was modified during iteration")
                }
                if (index == 0 || index > size) {
                    throw IllegalStateException("Cannot remove before calling next() or after the end of the list")
                }
                removeAt(index - 1)
                index--
                expectedModCount = modCount
            }
        }
    }

    fun toFloatArray(): FloatArray {
        return data.copyOf(size)
    }

    fun copyInto(target: FloatArray, position: Int = 0, offset: Int = 0, size: Int = this.size - position) {
        checkCopyBounds(position, size, offset, target.size)
        System.arraycopy(data, position, target, offset, size)
    }

    fun copyInto(target: FloatBuffer, position: Int = 0, offset: Int = 0, size: Int = this.size - position) {
        checkCopyBounds(position, size, offset, target.remaining())
        target.put(data, position, size)
    }

    fun trimToSize() {
        if (size < capacity) {
            data = data.copyOf(size)
        }
    }

    private fun checkCopyBounds(position: Int, size: Int, offset: Int, targetSize: Int) {
        if (position < 0 || position + size > this.size) {
            throw IndexOutOfBoundsException("Position: $position, Size: $size, List Size: ${this.size}")
        }
        if (offset < 0 || offset + size > targetSize) {
            throw IndexOutOfBoundsException("Offset: $offset, Size: $size, Target Remaining: $targetSize")
        }
    }

}