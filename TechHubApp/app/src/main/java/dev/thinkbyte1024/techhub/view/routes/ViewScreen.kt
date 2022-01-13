package dev.thinkbyte1024.techhub.view.routes

sealed class ViewScreen(val route: String) {
    object ProductList: ViewScreen(route = "list")
    object AddProduct: ViewScreen(route = "add")
    object EditProduct: ViewScreen(route = "edit")
    object DetailProduct: ViewScreen(route = "detail")

    fun withArgs(vararg args: String): String {
        return  buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
