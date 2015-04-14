# codepath-todo by naveenkollipara (naveengrad@gmail.com ~ Sr eCommerce Developer at Macys Inc ~ 949-573-6567)
V1.0 --My first android app developed for CodePath assignment
V1.1 --added DialogFragment usage on 4/13/2015

This is a demo application for adding, deleting, updating and displaying Todo items using SQLite and File persistence
minSDKVersion: API 16
Demo GIFs are included for both SQLite and File persistence
Time spent: 50 hours spent in total

Completed user stories:

 1) Required: User can view a list of Todo items <br>
 2) Required: User can add an item <br>
 3) Required: User can edit an item by clicking it. Edited item on edit screen can be updated <br>
 4) Required: User can remove an item by long clicking it. <br>
 
 5) Optional: Developed the app in two versions using File persistence and SQLite database persistence <br>
 6) Optional: Used custom adapters for displaying list items (star image is shown along with each list item) <br>
 7) Optional: CodePath image is used on Main Todo screen <br>
 8) Optional: Validations if user tries to add empty item or update an item with empty value <br>
 9) Optional: For user notifications used Alert Dialog (Edit item screen) and several Toast messages (Main screen) <br>
 10) Optional: At the time of app launch, 'crowd cheer' audio (Media Player) plays - Enjoy the applause..!! :) <br>
 11) Optional: Implemented plain vanilla DialogFragment functionality for editing item <br>
 
 
Notes:

 1) Though I have 11 years of experience in IT industry, mainly in back-end development, brand new to android development. <br>
 2) With pure passion to get short-listed and learn from expert instructors, researched and developed this app.<br>
 3) Tried my best to devote time for this, though busy with my 5 months baby and office work.<br>
 4) I came to know through a friend about this bootcamp only couple of weeks ago. If I had known it bit earlier and have more     free time.. would have completed other suggested improvements as well<br>
 
 Technical:
 1) Used Singleton (SQLite: single db instance) and Data Access Object design patterns<br>
 2) Used Enterprise application folder structure<br>
 3) Heavily documented and logged(using android.util.Log)<br>
 4) SQLite vs File: In AndroidManifest.xml please use below activities for SQLite vs File persistence        <br><br>
	SQLite: <br>
		Main activity name: .activity.TodoUsingSQLiteActivity<br>
		database name: items.db	<br>	
        File: <br>
		Main activity name: activity.TodoUsingSQLiteActivity<br>
		file name: todo.txt<br>
	SQLite and DialogFragment: <br>
		Main activity name: .activity.TodoUsingDialogctivity<br>
		database name: items.db	<br>

Walkthrough of all user stories:
<br>
<b>1) using SQLite persistence and DialogFragment (added in V1.1 on 4/13/2015)</b>
<p><a href="dialog-fragment.gif" target="_blank"><img src="dialog-fragment.gif" alt="SQLite persistence & DialogFragment demo Video Walkthrough" style="max-width:100%;"></a></p>
<br>
<b>2) using SQLite persistence</b>
<p><a href="sqlite-persistence.gif" target="_blank"><img src="sqlite-persistence.gif" alt="SQLite persistence demo Video Walkthrough" style="max-width:100%;"></a></p>


<b>3) using File persistence</b>
<p><a href="file-persistence.gif" target="_blank"><img src="file-persistence.gif" alt="File persistence demo Video Walkthrough" style="max-width:100%;"></a></p>
