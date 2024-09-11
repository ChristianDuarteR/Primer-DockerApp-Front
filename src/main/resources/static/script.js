document.addEventListener('DOMContentLoaded', () => {
    const sendButton = document.getElementById('sendButton');
    sendButton.addEventListener('click', sendMessage);
});

async function sendMessage() {
    const message = document.getElementById("messageInput").value;
    const responseContainer = document.getElementById("responseContainer");

    fetch('/sendmessage', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ message: message }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(data => {
           updateResponse(data);
        })
        .catch(error => {
            console.error('Hubo un problema con la solicitud:', error);
            responseContainer.innerText = `Error: ${error.message}`;
        });
}

function updateResponse(responseText) {
    const responseContainer = document.getElementById("responseContainer");
    const portPrefix = 'Servicio en el puerto ';
    const respondedPrefix = 'respondió: ';
    const portStartIndex = responseText.indexOf(portPrefix) + portPrefix.length;
    const portEndIndex = responseText.indexOf(' ', portStartIndex);
    const port = responseText.substring(portStartIndex, portEndIndex);
    const respondedStartIndex = responseText.indexOf(respondedPrefix) + respondedPrefix.length;
    const jsonResponse = responseText.substring(respondedStartIndex).trim();

    try {
        const messages = JSON.parse(jsonResponse);

        let tableHTML = '<table><thead><tr><th>ID</th><th>Mensaje</th><th>Fecha Creación</th></tr></thead><tbody>';
        messages.forEach(message => {
            tableHTML += `<tr>
                <td>${message.id}</td>
                <td>${JSON.parse(message.message).message}</td>
                <td>${message.dateCreated}</td>
            </tr>`;
        });
        tableHTML += '</tbody></table>';

        responseContainer.innerHTML = `<h3>Respuesta del servidor en el puerto ${port}:</h3>${tableHTML}`;
    } catch (error) {
        console.error('Error al procesar la respuesta JSON:', error);
        responseContainer.innerHTML = 'Error al procesar la respuesta.';
    }
}
