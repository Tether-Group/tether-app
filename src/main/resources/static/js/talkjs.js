<!-- minified snippet to load TalkJS without delaying your page -->
    (function(t,a,l,k,j,s){
    s=a.createElement('script');s.async=1;s.src="https://cdn.talkjs.com/talk.js";a.head.appendChild(s)
    ;k=t.Promise;t.Talk={v:3,ready:{then:function(f){if(k)return new k(function(r,e){l.push([f,r,e])});l
    .push([f])},catch:function(){return k&&new k()},c:l}};})(window,document,[]);


Talk.ready.then(async function () {
    let loggedInUser = await fetch ("/getUser/loggedInUser", {
        method: 'GET'
    }).then(function(request) {
        return request.json();
    })

    let myUserPhoto = document.getElementById('myPhotoURL').value;

    var me = new Talk.User({
        id: loggedInUser.id,
        name: loggedInUser.username,
        email: loggedInUser.email,
        photoUrl: myUserPhoto,
    });
    window.talkSession = new Talk.Session({
        appId: app_id,
        me: me,
    });

    //instead of doing this, use the document.getelementbyId to access that users info from the page (hidden input use th:value ---- .value)
    let otherUserID = document.getElementById("friendID").value;
    let otherUserUsername = document.getElementById('friendUsername').value;
    let otherUserEmail = document.getElementById('friendEmail').value;
    let otherUserPhotoURL = document.getElementById('friendPhotoURL').value;

    var other = new Talk.User({
        id: otherUserID,
        name: otherUserUsername,
        email: otherUserEmail,
        photoUrl: otherUserPhotoURL,
        welcomeMessage: 'Start the conversation...',
    });

    var conversation = talkSession.getOrCreateConversation(
        Talk.oneOnOneId(me, other)
    );
    conversation.setParticipant(me);
    conversation.setParticipant(other);

    var inbox = talkSession.createInbox({ selected: conversation });
    inbox.mount(document.getElementById('talkjs-container'));
});
