const allMatosElement = document.querySelectorAll(".verif");
const nonPresent = "nonPresent"; // Nom du style
const submitButton = document.querySelector("form[action='endVerif'] > input[type='submit']");

const updateMatos = []

for(let matosElement of allMatosElement) {
    matosElement.addEventListener('click', e => {
        if(matosElement.classList.contains(nonPresent)) {
            matosElement.classList.remove(nonPresent);
        } else {
            matosElement.classList.add(nonPresent);
        }
        if(updateMatos.includes(matosElement.id)) {
            updateMatos.splice(updateMatos.indexOf(matosElement.id), 1);
        } else {
            updateMatos.push(matosElement.id);
        }
    });
}

submitButton.addEventListener('click', e => {
    e.preventDefault();
    const input = document.querySelector("form[action='endVerif'] > input[name='matos']");
    input.value = updateMatos.join(",");
    document.querySelector("form[action='endVerif']").submit();
});