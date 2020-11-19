# TodoList
Mobile App Dev Project - take 2

TodoListActivity
- main screen, displays all lists
- has a button(add list) and a recyclerview(lists)
- Methods:
    - onCreate: sets view, creates toolbar, initializes buttons and recyclerview, creates layout manager for recyclerview, sets button to redirect to AddTaskActivity, calls getTodoLists
    - onResume: calls getTodoLists
    - getTodoLists: creates class GetTodoLists extending AsyncTask, executes an instance of that class
        -doInBackground: creates a List of all lists in the Database through the todoListDao, returns it
        -onPostExecute: executes after previous method, creates a new TodoListAdapter, feeds it the List, and links the adapter to the recyclerview
        
TaskActivity
- displays a single list and all its tasks
- has a button (add task), a recylerview(all tasks), and an int Id
- Methods:
    - onCreate: sets view, creates toolbar, sets parent list id, initializes FAB and recyleview, creates recycle view manager and links it, initializes click listener for button to send to AddTaskActivity, calls getTasks  
    - onResume: calls getTasks
    - getTasks:creates class GetTasks extending AsyncTask, executes an instance of that class
        -doInBackground: creates a List of all tasks in the Database through the taskDao, returns it
        -onPostExecute: executes after previous method, creates a new TaskListAdapter, feeds it the List, and links the adapter to the recyclerview
        
UpdateActivity
- creates dialog to update content of list or task
- has an edit text (new content), an int id, a string entityType, and a button (update)
- Methods:
    - onCreate: sets view, creates toolbar, initializes textview and button, sets id, sets text in edit text to current contents, gets entity type, on button click sends info to either updateTask or updateTodoList
    - updateTask: gets new text, creates class UpdateTask extending AsyncTask, creates instance and executes
        -doInBackground: creates a new Task with the info from the previous task using DatabaseClient and the id, sets the name to the new text, uses DatabaseClient to update that task with the newly created one
        -onPostExecute: executes after previous method, finishes
    - updateTodoList: gets new text, creates class UpdateTodoList extending AsyncTask, creates instance and executes
        -doInBackground: creates a new TodoList with the info from the previous task using DatabaseClient and the id, sets the name to the new text, uses DatabaseClient to update that task with the newly created one
        -onPostExecute: executes after previous method, finishes
        
AddTaskActivity
- adds a new task
- has an edit text (new content) and a int id
- Methods:
    - onCreate: sets view, creates toolbar, initializes edittext and button, sets id, sets listener for save button to call saveNewTask
    - saveNewTask: gets new text, displays an error if the name is empty, creates class SaveTask extending AsyncTask, creates instance and executes
        -doInBackground: creates a new Task with the new name and default info using DatabaseClient and the id, uses DatabaseClient to add it to the list
        -onPostExecute: executes after previous method, finishes
        
AddTodoListActivity
- adds a new list
- has an edit text (new list title)
- Methods:
    - onCreate: sets view, creates toolbar, initializes edittext and button, sets id, sets listener for save button to call saveNewTodoList
    - saveNewTodoList: gets new text, displays an error if the name is empty, creates class SaveTodoList extending AsyncTask, creates instance and executes
        -doInBackground: creates a new TodoList with the new name, uses DatabaseClient to add it to the list
        -onPostExecute: executes after previous method, directs back to TodoListActivity, finishes


DatabaseClient
- checks to see if db already exists
- has a context, an instance of itself, and a database
- creates a new Room db, check to see if one exists, if one does then it creates, if one doesn’t it returns

TaskDao
- database access for tasks
- can insert, delete, edit, and return all or a specific task based on id

TodoListDao
- database access for lists
- can insert, delete, edit, and return all tasks in a list by list id or a specific task based on task id

TodoListAppDatabase
- creates singelton database
- links to: TaskDao, TodoListDao


Task
- model for a task
- contains getters and setters
- properties: id, name, isChecked, todoListId
- Links to: TaskDao

TodoList
- model for a list
- contains getters and setters
- properties: id, name
- Links to: TodoListDao


TodoListAdapter
- displays and manages lists
- has a context, a List (of lists), and an activity
- Methods:
    - onCreateViewHolder: creates view based on layout inflater from given context, returns new TodoListViewHandler based on that
    - onBindViewHolder: creates new TodoList  at the given position in the List, uses TodoListViewHandler to set the text to the given title, sets the click listener to navigate to the intent that corresponds to the list, creates delete and update buttons
    - getItemCount: returns List size
    - deleteTodoList: takes TodoList to delete, creates DeleteTodoList class extending AsyncTask, creates instance and executes
        -doInBackground: uses DatabaseClient to remove the list
        -onPostExecute: executes after previous method, directs back to TodoListActivity, finishes
- Class:
    - TodoListViewHandler: (extends recyclerview and implements onclick) 
        -has a textview (name), an image view (to delete), and another image view (to edit)
        -Methods: 
            - constructor: creates new instance with values from the view
            - onClick: need for implements
            
TaskListAdapter
- displays and manages tasks
- has a context, a List (of tasks), and an activity
- Methods:
    - onCreateViewHolder: creates view based on layout inflater from given context, returns new TaskViewHandler based on that
    - onBindViewHolder: creates new Task at the given position in the List, uses TaskViewHandler to set the text to the given title, creates delete and update buttons, sets checkbox listener, sets if checkbox is checked or not
    - getItemCount: returns List size
    - deleteTodoList: takes TaskList to delete, creates DeleteTask class extending AsyncTask, creates instance and executes
        -doInBackground: uses DatabaseClient to remove the task
        -onPostExecute: executes after previous method, directs back to TodoListActivity, finishes
    - updateTask: takes TaskList to update, creates UpdateTask class extending AsyncTask, creates instance and executes
        -doInBackground: uses DatabaseClient to update the task
        -onPostExecute: executes after previous method, finishes
- Class:
    - TaskListViewHandler: (extends recyclerview and implements onclick) 
        -has a textview (name), an image view (to delete), a checkbox, and another image view (to edit)
        -Methods: 
            - constructor: creates new instance with values from the view
            - onClick: need for implements
