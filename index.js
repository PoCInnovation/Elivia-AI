const { containerBootstrap } = require('@nlpjs/core-loader');
const { Nlp } = require('@nlpjs/nlp');
const readline = require('readline');


(async() => {
  const container = await containerBootstrap()
  const nlp = new Nlp({ container })

  nlp.load("./model.nlp").then()

  console.log('NLP model loaded')
  const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
    prompt: 'elivia> '
  });
  rl.prompt();
  rl.on('line', async (line) => {
    const res = await nlp.process(line.trim());
    console.log("intent: ", res.intent);
    console.log(res.answer);
    rl.prompt();
  }).on('close', () => {
    console.log('Have a great day!');
    process.exit(0);
  });
})();

