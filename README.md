# MyBrowserAndroidApp
Test browser app
Description:

This is a simple android webview app made in Kotlin which will load google Url by default.
It has all kotlin features like:
1) Code cleanup:
All files are organized in separate package and code is refactored, like activity file in activity package, utility file in utility package. 

2) Use of Extension function : 
In Extensions.kt file we are using extension function showToast() is used with syntax like Context.showToast() 

3) Use of Default parameters:
In showToast() function in Extensions.kt file, we are using default parameter for duration, 
advantage for default parameter is that there is no need to pass second parameter if we want to show toast for short duration, 
however if we want to show toast for longer duration, we can give new value, and default value will be overriddden by new value.

4) Null safety operators:
We are doing null checks by using:
a)Safe call (?.) and
b) Not Null Assertion operator(!!)
in MainActivity.kt file
 
5) Object declaration and companion object
Use of companion object: An object declaration inside a class is marked with companion keyword in MainActivity.kt file
Use of object declaration: in Utilty.kt file

This browser app, will have functions like
1) It loads google url by default
2) It has Progress Bar whose visibility will be visible when page starts loading and whose visibility will be gone when page loading is done
3) It handles clicking actions in webview like when we click on Call link in webview then it will navigate to Phone app.
4) It handles back press, if webview has back history, then it goes back, 
otherwise if back history is not available then it asks user to exit the app or stay in the app 
5) It has a download listener added in webview to download any file from website, 
also we are adding storage permisison request before starting download for M and above devices.

