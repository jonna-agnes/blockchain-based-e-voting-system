document.addEventListener('DOMContentLoaded', function() {
    const voteForm = document.getElementById('voteForm');

    if (voteForm) {
        voteForm.addEventListener('submit', function(event) {
            event.preventDefault();

            const voterId = document.getElementById('voterId').value;
            const party = document.getElementById('party').value;

            fetch('http://localhost:4567/vote', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    'voterId': voterId,
                    'party': party
                })
            })
            .then(response => response.text())
            .then(data => {
                document.getElementById('result').innerHTML = data;
            })
            .catch(error => {
                console.error('Error:', error);
            });
        });
    }
});