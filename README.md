# user-info

An app that will display user info of employees

# User Info Screen:
![alt text](https://github.com/PremAru/user-info/blob/main/images/image1.png)

![alt text](https://github.com/PremAru/user-info/blob/main/images/image2.png)

![alt text](https://github.com/PremAru/user-info/blob/main/images/image3.png)



## Getting Started

Install Android Studio

	http://developer.android.com/sdk/index.html

Install Android SDK & buildtools

	Android 8.0 (API 26)
	buildtools 26.0.1 and above

Open workspace

	Clone this repository and import into Android Studio
	git clone git@github.com:PremAru/user-info.git
	
	Android Studio -> File -> Open

Run Unit Tests

	$ ./gradlew build

Build project

	$ gradlew build
  
# Language
	Kotlin
	
# Development
The project follows MVVM architecture with AndroidX libraries. Dagger is used heavily for dependancy injection as it helps to avoid error-prone boilerplate code and makes unit testing efficient. 
	
The project also implements interceptors for logging, caching and offline management. Also, when the user scrolls to the bottom of the list the subsequent pages are fetched via pagination. 
	
The images in the application are lazy loaded using Picasso library which is also injected by Dagger

TDD is followed for the development of the application. Red, Green, Refactor approach of TDD is followed to efficiantly model the classes and inject dependancies.
    
# Libraries used:

*	RxAndroid

*	Retrofit

*	OkHttp

*	Dagger2

*	Mockito

*	Timber

*	Expresso

*	GSON
