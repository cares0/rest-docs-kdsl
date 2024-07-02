package cares.restdocskdsl.ksp.writer

class BodyElementNameResolver {

    private val containerMap: MutableMap<String, ResolvedElementContainer> = mutableMapOf()

    fun getNextName(elementName: String): String {
        var container = containerMap[elementName]
        if (container == null) {
            val newContainer = ResolvedElementContainer(elementName)
            containerMap[elementName] = newContainer
            container = newContainer
        }

        return container.getNextName()
    }

    fun getCurrentName(elementName: String): String {
        var container = containerMap[elementName]
        if (container == null) {
            val newContainer = ResolvedElementContainer(elementName)
            containerMap[elementName] = newContainer
            container = newContainer
        }

        return container.getCurrentName()
    }

    data class ResolvedElementContainer(
        val elementName: String,
    ) {
        private var topIndex = 0
        private var indexQueue: ArrayDeque<Int> = ArrayDeque()

        fun getNextName(): String {
            indexQueue.addFirst(topIndex++)
            val temp = "${elementName}_${indexQueue.first()}"
            return temp
        }

        fun getCurrentName(): String {
            val currentIndex = indexQueue.removeLastOrNull() ?: 0
            return "${elementName}_${currentIndex}"
        }
    }
}