function showTextInputs(fileInput) {
    const textInputs = fileInput.parentNode.querySelectorAll('input[type="text"]');
    textInputs.forEach(input => input.classList.add('visible'));
}

function showSuccessMessage() {
    const successMessage = document.querySelector('.success-message');
    successMessage.style.display = 'block';
}

document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("productForm");
    form.addEventListener("submit", function(event) {
        event.preventDefault();
        showSuccessMessage();
        setTimeout(() => {
            form.submit();
        }, 1000);
    });
});