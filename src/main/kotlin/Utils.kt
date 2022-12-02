object Utils {
    fun getInput(resourcePath: String): String {
        return Utils::class.java.getResource(resourcePath).readText()
    }
}