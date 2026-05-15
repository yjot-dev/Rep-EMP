package com.yjotdev.empprimaria.domain.utils

import com.yjotdev.empprimaria.domain.model.ProjectModel
import com.yjotdev.empprimaria.R

object Projects {
    val list = listOf(
        ProjectModel(
            title = R.string.project_title1,
            imagePath = "https://cdn.pixabay.com/photo/2016/11/19/10/01/art-1838414_960_720.jpg",
            description = R.string.project_description1
        ),
        ProjectModel(
            title = R.string.project_title2,
            imagePath = "https://cdn.pixabay.com/photo/2017/08/07/16/41/reading-2605540_1280.jpg",
            description = R.string.project_description2
        ),
        ProjectModel(
            title = R.string.project_title3,
            imagePath = "https://cdn.pixabay.com/photo/2022/07/22/12/51/children-7338082_1280.jpg",
            description = R.string.project_description3
        ),
        ProjectModel(
            title = R.string.project_title4,
            imagePath = "https://cdn.pixabay.com/photo/2017/05/31/09/41/a-grateful-heart-2359746_960_720.jpg",
            description = R.string.project_description4
        ),
        ProjectModel(
            title = R.string.project_title5,
            imagePath = "https://cdn.pixabay.com/photo/2018/10/22/18/02/teacher-3765909_960_720.jpg",
            description = R.string.project_description5
        ),
        ProjectModel(
            title = R.string.project_title6,
            imagePath = "https://cdn.pixabay.com/photo/2016/11/20/08/58/books-1842261_960_720.jpg",
            description = R.string.project_description6
        )
    )
}