
// Shared script for login, register, and todos pages
const SERVER_URL = "http://localhost:8080";

// Login page logic
function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch(`${SERVER_URL}/auth/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email,
                password
            })
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => {
                    throw new Error(err.message || "Sorry! Login Failed");
                });
            }
            return response.json();
        })
        .then(data => {
            localStorage.setItem("token", data.token);
            window.location.href = "todos.html";
        })
        .catch(error => {
            alert(error.message);
        });
}

// Register page logic
function register() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch(`${SERVER_URL}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email,
                password
            })
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text || "Sorry! Registration Failed");
                });
            }
            return response.text();
        })
        .then(data => {
            alert(data);
            window.location.href = "login.html";
        })
        .catch(error => {
            alert(error.message);
        });
}

// Function to create a Todo card element (from your provided code)
function createTodoCard(todo) {
    const card = document.createElement("div");
    card.className = "todo-card";

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.checked = todo.isCompleted;
    checkbox.addEventListener("change", function() {
        const updatedTodo = {
            ...todo,
            isCompleted: checkbox.checked
        };
        updateTodoStatus(updatedTodo);
    });

    const span = document.createElement("span");
    span.textContent = todo.title;
    if (todo.isCompleted) {
        span.style.textDecoration = "line-through";
        span.style.color = "#aaa";
    }

    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Delete";
    deleteBtn.onclick = function() {
        deleteTodo(todo.id);
    };

    card.appendChild(checkbox);
    card.appendChild(span);
    card.appendChild(deleteBtn);

    return card;
}

// Todos page logic
function loadTodos() {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const todoList = document.getElementById("todo-list");
    todoList.innerHTML = "";

    fetch(`${SERVER_URL}/api/v1/todo`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
        })
        .then(response => {
            if (!response.ok) {
                if (response.status === 401) {
                    alert("Your session has expired. Please log in again.");
                    localStorage.removeItem("token");
                    window.location.href = "login.html";
                    return;
                }
                throw new Error("Failed to load todos");
            }
            return response.json();
        })
        .then(todos => {
            if (todos.length === 0) {
                todoList.innerHTML = "<p>You have no todos yet.</p>";
            } else {
                todos.forEach(todo => {
                    const todoCard = createTodoCard(todo);
                    todoList.appendChild(todoCard);
                });
            }
        })
        .catch(error => {
            alert(error.message);
        });
}

function addTodo() {
    const token = localStorage.getItem("token");
    const input = document.getElementById("new-todo");
    const title = input.value;
    if (!title.trim()) {
        alert("Todo title cannot be empty!");
        return;
    }

    const newTodo = {
        title,
        isCompleted: false,
    };

    fetch(`${SERVER_URL}/api/v1/todo/create`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(newTodo)
    })
    .then(response => {
        if (!response.ok) {
            // Check for unauthorized status code and redirect
            if (response.status === 401) {
                alert("Your session has expired. Please log in again.");
                localStorage.removeItem("token");
                window.location.href = "login.html";
                return;
            }
            throw new Error("Oops! Failed to Add the data");
        }
        return response.json();
    })
    .then(() => {
        input.value = "";
        loadTodos();
    })
    .catch(error => {
        alert(error.message);
    });
}
function updateTodoStatus(todo) {
    const token = localStorage.getItem("token");
    fetch(`${SERVER_URL}/api/v1/todo`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify(todo)
        })
        .then(response => {
            if (!response.ok) {
                if (response.status === 401) {
                    alert("Your session has expired. Please log in again.");
                    localStorage.removeItem("token");
                    window.location.href = "login.html";
                    return;
                }
                throw new Error("Oops! Failed to Update the data");
            }
            return response.json();
        })
        .then(() => loadTodos())
        .catch(error => {
            alert(error.message);
        });
}

function deleteTodo(id) {
    const token = localStorage.getItem("token");
    fetch(`${SERVER_URL}/api/v1/todo/${id}`, {
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${token}`
            },
        })
        .then(response => {
            if (!response.ok) {
                if (response.status === 401) {
                    alert("Your session has expired. Please log in again.");
                    localStorage.removeItem("token");
                    window.location.href = "login.html";
                    return;
                }
                throw new Error("Oops! Failed to Delete");
            }
            return response.text();
        })
        .then(() => loadTodos())
        .catch(error => {
            alert(error.message);
        });
}



