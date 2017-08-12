# Pre-work - *SimpleTodo*

**SimpleTodo** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Alitsiya Yusupova**

Time spent: **11** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/r73Blnd.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** I found that using Android Platform for an Intro project is very easy, I don't have to worry about exporting basic libraries since majority of components are incleded into Android SDK. Structure of Android projects makes it easy to navigate and read/write code, included to Android Studio Emulator and debugger tool makes it easy to execute and debug your code. Documentation is good and amount of StackOverflow information is tremendouse.
Working with Android views is challenging since I don't know much about it to fully understand how it all works and behave. UI is similar to web-development where you use html/xml to add different UI components. And googling how to make different element in layout to be exaclty where you want it to be is the same way painful as building web ui.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** ArrayAdapter is a medium between view and the data that needs to be placed inside this veiw. ArrayAdapter converts ListView into individual Views. ArrayAdapter helps to keep only currently visible and next/previous items in memory and not to load all the data. 
ConvertView used to reuse views in listview if possible.

## Notes

I wanted to have Drag-n-Drop to change Todo item priority and Swipe to delete features, so I spent some time researching and started using RecyclerView instead of ListView to hold my Todo items. RecyclerView and ListView have a small amount of common features, so I basically rewrote whole logic with RecyclerView.

## License

    Copyright [2017] [Alitsiya]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.