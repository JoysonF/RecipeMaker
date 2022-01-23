# Recipe Sharing Mobile Coding Challenge

The project demonstrates various food collections and the recipes on how to prepare them. It shows the ingredients and the steps to prepare them with images.

## Features - The Recipe Sharing app 
1) List of Food:
   - The first page of app lists different Food Collections.
2) Food Details
   - Shows the details of the Food Collections, read more/read less feature when the description shown is less/more respectively.
3) Recipe List
   - Shows the list of all recipes available for a given collection.
4) Recipe Details
   - Shows the detail section of Recipes with images in a viewpager
5) Ingredients
   - Display the ingredients needed to create the recipe
6) Steps
   - Shows detailed steps on how to create the recipe with images in a viewpager.

## Future Enhancements
1) Considering the time constraints , some of the future enhancements that can be considered are as follows:
* Pagination - The api currently does not support pagination, we can implement pagination support in the app especially in List of Collections and in List of Recipes.
* Animations - We can use Motion animation to make the UI more user engaging.
* UI Test - We can write UI and Integration tests and more unit test to make the app more stable and find bugs


## Android Concepts
1) I have used MVVM architecture to create this project along with Android Architecture components like ViewModel, LiveData... 
2) For dependency injection, i have used Dagger Hilt. This helps to better manage the dependencies in the app.


## Screenshots
1) App architecture

   ![mvvm-arch](https://user-images.githubusercontent.com/6438183/150671806-f234d247-8199-4429-b92a-837d79a240de.png)

2) App folder structure

   ![folder structure](https://user-images.githubusercontent.com/6438183/150672652-9ca769ed-e147-4d2e-b98f-7ccd97d43422.png)
   
3) Food Collection

   ![list_1](https://user-images.githubusercontent.com/6438183/150672436-cf2927ba-76f3-4b91-9bb7-eaec1a84b4c3.jpeg)

4) Food Details

   ![food_details_1](https://user-images.githubusercontent.com/6438183/150672439-5c01be2f-7a20-43d4-bae4-0c3fba4e48b4.jpeg)

   ![Food_details_2](https://user-images.githubusercontent.com/6438183/150672443-7d234913-8923-4023-8eed-b74688b5ecc2.jpeg)
   
5) Recipe Collection

   ![Steps_1](https://user-images.githubusercontent.com/6438183/150672500-abcdb433-b13c-46c3-936e-a047b86a194f.jpeg)

   ![Steps_2](https://user-images.githubusercontent.com/6438183/150672449-2763e81e-e664-4698-85ec-359b10ddf68d.jpeg)
   38183/150672447-19df0eab-d8b9-48e7-8e9f-e0d8e1b15d0c.jpeg)

   ![Steps_3](https://user-images.githubusercontent.com/6438183/150672520-c1fe3c26-1153-4049-b4c5-32f63d00c275.jpeg)
   
6) Tests

   ![tests](https://user-images.githubusercontent.com/6438183/150672658-24be4916-c823-400d-9939-30e0a279ca02.png)


## Local Development

Here are some useful Gradle/adb commands for executing this project:

* `./gradlew clean build` - Build the entire example and execute unit and integration tests plus lint check.
* `./gradlew installDebug` - Install the debug apk on the current connected device.

## Libraries

Hilt: https://developer.android.com/training/dependency-injection/hilt-android

MVVM Architecture : https://developer.android.com/jetpack/guide

Coroutines: https://developer.android.com/kotlin/coroutines

Parallax: https://github.com/matrixxun/ImmersiveDetailSample.git

View Binding: https://developer.android.com/topic/libraries/view-binding

Glide: https://github.com/bumptech/glide

Retrofit: https://square.github.io/retrofit/


## Troubleshooting

If you're experiencing access issues when running the app, feel feel to raise issues in the github Issues section here ![https://github.com/JoysonF/RecipeMaker/issues]
