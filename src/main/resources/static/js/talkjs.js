<!-- minified snippet to load TalkJS without delaying your page -->
    (function(t,a,l,k,j,s){
    s=a.createElement('script');s.async=1;s.src="https://cdn.talkjs.com/talk.js";a.head.appendChild(s)
    ;k=t.Promise;t.Talk={v:3,ready:{then:function(f){if(k)return new k(function(r,e){l.push([f,r,e])});l
    .push([f])},catch:function(){return k&&new k()},c:l}};})(window,document,[]);


const getAgent = async () => {
    const response = await fetch('http://localhost:8080/getUser?candywandy');
    const data = await response.json();
    let agent = new Talk.User({
        id: data.id,
        name: data.name,
        // photoUrl: data.dp,
        email: data.email,
        // role: data.role
    });
    return agent;
}
const getUser = async () => {
    const response = await fetch('http://localhost:8080/getUser?StephenAdmin');
    const data = await response.json();
    let user = new Talk.User({
        id: data.id,
        name: data.name,
        // photoUrl: data.dp,
        email: data.email,
        // role: data.role
    });
    return user;
}

(async function() {
    await Talk.ready;
    let agent = await getAgent();
    let user = await getUser();
    const session = new Talk.Session({
        appId: 'thVjgOD8',
        me: user,
    });
    var conversation = session.getOrCreateConversation(Talk.oneOnOneId(user, agent))
    conversation.setAttributes({
        welcomeMessages: ["Start the conversation.."]
    })
    conversation.setParticipant(user);
    conversation.setParticipant(agent);

    var inbox = session.createInbox(conversation);
    inbox.mount(document.getElementById("talkjs-container"));
}());