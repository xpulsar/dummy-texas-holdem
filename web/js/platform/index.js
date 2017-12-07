/**
 * Created by dummy-team
 * 2017-10-12
 */
var phoneNumber;
var token;

$(document).ready(function () {
    // get phoneNumber and token
    phoneNumber = getParameter('phoneNumber') || localStorage.getItem('phoneNumber');
    token = getParameter('token') || localStorage.getItem('token');
    localStorage.setItem('phoneNumber', phoneNumber);
    localStorage.setItem('token', token);

    // get board list
    listTheBoards();
});

function listTheBoards() {
    $.ajax({
        url: '/board/list_boards',
        headers: {"phone-number": phoneNumber, "token": token},
        type: 'POST',
        dataType: 'json',
        data: {
            status: 0,
            gameName: "texas_holdem"
        },
        timeout: 20000,
        success: function (response) {
            if (response.status.code === 0) {
                var boardList = response.entity;
                onTheBoardsListed(boardList);
            } else {
                console.log('list boards failed');
            }
        },
        error: function () {
            console.log('list boards failed');
        }
    });
}

function onTheBoardsListed(boardList) {
    if (null !== boardList) {
        document.getElementById('board_list').innerHTML = '';
        var boardListContent = '';
        for (var i = 0; i < boardList.length; i++) {
            boardListContent += '<div><a href="#" onclick="joinLive(\'' + boardList[i].ticket + '\');">' +
                boardList[i].ticket + '</a></div>'
        }
        $('#board_list').append(boardListContent);
    }
}

function joinLive(ticket) {
    console.log(ticket);
}
