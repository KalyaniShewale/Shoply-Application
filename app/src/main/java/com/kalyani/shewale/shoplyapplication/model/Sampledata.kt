package com.kalyani.shewale.shoplyapplication.model

object SampleData {

    private val alex = Seller("Alex Morgan", "+447700900123", "alex@email.com")
    private val priya = Seller("Priya Sharma", "+919876543210", "priya@email.com")

    val products = listOf(
        Product("1", "Wireless headphones", "Over-ear headphones with noise cancelling and a 30 hour battery. Comes with the original case and cable. Used for six months.", 59.0, "Electronics", listOf("a", "b", "c"), alex),
        Product("2", "Denim jacket", "Size M, barely worn. Classic blue denim with a soft inner lining, suitable for spring and autumn.", 25.0, "Fashion", listOf("a", "b", "c"), priya),
        Product("3", "Desk lamp", "Adjustable arm with warm and cool light modes. USB powered and easy to fold away.", 14.5, "Home", listOf("a", "b", "c"), alex),
        Product("4", "Mechanical keyboard", "Compact 75 percent layout with brown switches. Includes a USB-C cable and spare keycaps.", 45.0, "Electronics", listOf("a", "b", "c"), priya),
        Product("5", "Leather backpack", "Fits a 15 inch laptop. Two main compartments and a hidden zip pocket on the back.", 38.0, "Fashion", listOf("a", "b", "c"), alex),
        Product("6", "Ceramic plant pot", "Hand painted pot with a drainage hole and saucer. Fits a medium sized plant.", 9.0, "Home", listOf("a", "b", "c"), priya)
    )

    val recommended = listOf(
        Product("r1", "Fjallraven Backpack", "Your perfect pack for everyday use and walks in the forest.", 109.95, "Fashion", listOf("a")),
        Product("r2", "Slim Fit T-Shirt", "Slim fitting style, contrast raglan long sleeve, three button henley placket.", 22.3, "Fashion", listOf("a")),
        Product("r3", "Cotton Jacket", "Great outerwear for spring, autumn and winter.", 55.99, "Fashion", listOf("a"))
    )

    fun findById(id: String): Product? = (products + recommended).find { it.id == id }
}