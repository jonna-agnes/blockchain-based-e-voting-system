document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("resultsForm");
    const resultsContainer = document.getElementById("resultsContainer");

    form.addEventListener("submit", function(event) {
        event.preventDefault();
        const password = document.getElementById("password").value;

        fetch("/viewresults", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                password: password
            })
        })
        .then(response => response.text())
        .then(data => {
            resultsContainer.innerHTML = data;
        })
        .catch(error => {
            console.error("Error:", error);
            resultsContainer.innerHTML = "An error occurred while fetching results.";
        });
    });
});