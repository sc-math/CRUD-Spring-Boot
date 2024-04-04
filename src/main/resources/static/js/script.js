// URLs
const URL = "http://localhost:8080/persons"

// Dados do Form
const personForm = document.querySelector("#person-form");
const nameInput = document.querySelector("#fname")
const emailInput = document.querySelector("#fEmail")
const ageInput = document.querySelector("#fAge")
const cpfInput = document.querySelector("#fCpf")

// Add Row on Table
function addRow(person){
    let table = document.querySelector("#personsTable")

    let row = table.insertRow(-1)

    let name = row.insertCell(0)
    let email = row.insertCell(1)
    let age = row.insertCell(2)
    let cpf = row.insertCell(3)

    name.innerText = person.name
    email.innerText = person.email
    age.innerText = person.age
    cpf.innerText = person.cpf

    console.log(row)
}

// POST Functions
async function postPerson(person){

    let personJSON = JSON.stringify(person)

    const response = await fetch(URL+"/register", {
        method: "POST",
        body: personJSON,
        headers: {
            "Content-type": "application/json",
        }
    })

    if(response.status === 201){
        nameInput.value = ""
        emailInput.value = ""
        ageInput.value = ""
        cpfInput.value = ""

        alert("Pessoa cadastrada com sucesso!")

        addRow(person)
    }
    else{
        alert("Um erro ocorreu!\n")
        console.log(response)
    }
}

personForm.addEventListener("submit", (e) => {
    e.preventDefault();

    let person = {
        name: nameInput.value,
        email: emailInput.value,
        age: ageInput.value,
        cpf: cpfInput.value,
    }

    postPerson(person)
})

// Get All Persons Function
async function getAllPersons(){
    const response = await fetch(URL+"/get")

    console.log(response)

    const data = await response.json()

    console.log(data)

    data.map((person) => {
        addRow(person)
    })
}

getAllPersons()