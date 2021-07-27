# Elivia-AI


## The future Voice assistant of the [/e/ OS](https://e.foundation/e-os/)

Take control of your of your smartphone with your voice. Like Google Assistant, Alexa or Cortana, Elivia is a voice assistant but free and open-source.
It can help you to do your everyday task.
It is private by default and completely customizable.


### How it's work ?
We use a mobile app made with kotlin, and with the help of the [Vosk](https://alphacephei.com/vosk/android) and the french model of [Paul Guyot](https://alphacephei.com/vosk/models) to translate your speech to text.
To detect the intention and understande what you want we use RASA as NLU and Spacy as NLP. After this we extract all the entities (like the personne you want to call).

## Installation:

### Server Side

	git clone https://github.com/PoCInnovation/Elivia-AI.git
	cd Elivia-AI
	git switch RASA+Spacy
	rasa

### Android app

	git clone https://github.com/PoCInnovation/Elivia-AI.git
	cd Elivia-AI
	git switch RASA+Spacy
	open the project with android studion

## What Elivia understand

| Intent | Server | App | Descritption|
| :------| :----- | :-- | :-----------|
| hello | :heavy_check_mark: | :x: | say hello |
| mood | :heavy_check_mark: | :x: | ask your mood |
| open | :heavy_check_mark: | :x: | open an application on the phone |
| search | :heavy_check_mark: | :x: | search somthing on the Web |
| alarm | :heavy_check_mark: | :x: | set an alarm |
| clock | :heavy_check_mark: | :x: | get the time for anywhere |
| chrono | :heavy_check_mark: | :x: | start a chrono |
| weather | :heavy_check_mark: | :x: | get the weather for anywhere |
| plan | :heavy_check_mark: | :x: | start the naviguation map to go anywhere |
| call | :heavy_check_mark: | :x: | make a call to one of your contact |
| message | :heavy_check_mark: | :x: | text a message to one of your contact |
| sport | :heavy_check_mark: | :x: | start a sport session |
| music | :heavy_check_mark: | :x: | start a music |
| calculator | :heavy_check_mark: | :x: | make calcul |
| reminder | :heavy_check_mark: | :x: | add a reminder |


## Contributors

    [@RedGinor](https://github.com/RedGinor) Mark BEKKER
    [@celestecote21](https://github.com/celestecote21) Céleste Cote
    [@Mikatech](https://github.com/Mikatech) Mikael VALLENET

