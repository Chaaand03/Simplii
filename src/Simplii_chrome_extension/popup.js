document.addEventListener('DOMContentLoaded', function() {
    const addTaskButton = document.getElementById('addTask');
    const taskInput = document.getElementById('task');
    const taskList = document.getElementById('taskList');
  
    // Function to create a task element with delete button
    function createTaskElement(task) {
      const taskElement = document.createElement('div');
      taskElement.classList.add('task-item');
  
      const textElement = document.createElement('span');
      textElement.textContent = task;
  
      const deleteButton = document.createElement('button');
      deleteButton.textContent = '❌'; // Use any icon or text for the delete button
      deleteButton.addEventListener('click', function() {
        deleteTask(task);
      });
  
      taskElement.appendChild(textElement);
      taskElement.appendChild(deleteButton);
  
      return taskElement;
    }
  
    // Function to delete a task
    function deleteTask(task) {
      let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
      tasks = tasks.filter(item => item !== task);
      localStorage.setItem('tasks', JSON.stringify(tasks));
      displayTasks(tasks);
    }
  
    // Function to display tasks
    function displayTasks(tasks) {
      taskList.innerHTML = ''; // Clear previous tasks
      tasks.forEach(function(task) {
        const taskElement = createTaskElement(task);
        taskList.appendChild(taskElement);
      });
    }
  
    // Event listener for adding a task
    addTaskButton.addEventListener('click', function() {
      const task = taskInput.value.trim();
  
      if (task !== '') {
        let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
        tasks.unshift(task); // Add the new task to the beginning of the array
        localStorage.setItem('tasks', JSON.stringify(tasks));
        displayTasks(tasks);
  
        // Clear input field after storing task
        taskInput.value = '';
      }
    });
  
    // Load and display tasks when the popup is opened
    const storedTasks = JSON.parse(localStorage.getItem('tasks')) || [];
    displayTasks(storedTasks);
  });
  
  
  
  
  