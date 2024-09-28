package com.example.notespro

interface Destinations{
    val Route : String
}

object Add:Destinations{
    override val Route = "Add Notes"
}

object Edit:Destinations{
    override val Route = "Edit Notes"
}

object Home:Destinations{
    override val Route = "Home"
}

object ViewNote:Destinations{
    override val Route = "Note"
}