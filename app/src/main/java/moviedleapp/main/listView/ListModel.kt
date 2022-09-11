package moviedleapp.main.listView

class ListModel(private var name: String, private var imageSource: Int) {

    fun getTitle(): String {
        return name
    }

    fun getImage(): Int {
        return imageSource
    }
}
