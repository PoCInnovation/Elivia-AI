const { NlpManager } = require('node-nlp');

const manager = new NlpManager({ languages: ['fr'], forceNER: true});
// Adds the utterances and intents for the NLP
manager.addDocument('fr', 'Bonjour, comment allez vous', 'greeting.hello');
manager.addDocument('fr', 'Salut', 'greeting.hello');
manager.addDocument('fr', 'content de vous rencontrez', 'greeting.hello');
manager.addDocument('fr', 'Nous nous voyons plus tard', 'greeting.bye');
manager.addDocument('fr', 'aurevoir', 'greeting.bye');
manager.addDocument('fr', 'a bientot', 'greeting.bye');
manager.addDocument('fr', 'quel temps fait-il ?', 'action.meteo');
manager.addDocument('fr', 'pleut-il demain', 'action.meteo');
manager.addDocument('fr', 'quel temps il fera demain', 'action.meteo');
manager.addDocument('fr', 'ajout un rappel pour demain', 'action.rappel');
manager.addDocument('fr', 'rappele moi d\'appeler mami demain', 'action.rappel');
manager.addDocument('fr', 'met un rappel pour dans 5 min', 'action.rappel');

// manager.addDocument('en', 'rmail', 'greetings.responce');

// Train also the NLG
// manager.addAnswer('fr', 'greetings.bye', 'Till next time');
// manager.addAnswer('fr', 'greetings.bye', 'see you soon!');
// manager.addAnswer('fr', 'greetings.hello', 'Hey there!');
// manager.addAnswer('fr', 'greetings.hello', 'Greetings!');

// Train and save the model.
(async() => {
    await manager.train();
    manager.save();
    // let response = await manager.process('en', 'I should go now');
    // console.log(response);
    // response = await manager.process('en', 'My email is something@somehost.com please write me');
    // console.log(response);
})();