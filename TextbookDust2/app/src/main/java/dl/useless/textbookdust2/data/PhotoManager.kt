package dl.useless.textbookdust2.data

import dl.useless.textbookdust2.R

class PhotoManager {
    var nameList = arrayListOf<String>(
        "CT Spawn","A Bomb Site","A Long",
        "Outside Long","T Ramp","T Spawn",
        "T Platform","Outside Tunnels","Upper Tunnel",
        "B Bomb Site","CT Mid","Mid")

    var idList = arrayListOf<Int>(
        R.drawable.p01, R.drawable.p02,
        R.drawable.p03, R.drawable.p04,
        R.drawable.p05, R.drawable.p06,
        R.drawable.p07, R.drawable.p08,
        R.drawable.p09, R.drawable.p10,
        R.drawable.p11, R.drawable.p12)

    var thumbnailList = arrayListOf<Int>(
        R.drawable.t01, R.drawable.t02,
        R.drawable.t03, R.drawable.t04,
        R.drawable.t05, R.drawable.t06,
        R.drawable.t07, R.drawable.t08,
        R.drawable.t09, R.drawable.t10,
        R.drawable.t11, R.drawable.t12
    )

    fun makePhotos():List<Photo> {
        var photoList= mutableListOf<Photo>()
        nameList.forEachIndexed { index, s ->
            photoList.add(Photo(nameList[index],thumbnailList[index],idList[index]))
        }
        return photoList.toList()
    }
}