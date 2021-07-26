# Elivia-AI


## The future Virtual assistant of the [/e/OS](https://e.foundation/e-os/)

Contole your phone with your voice, alway with privacy.


## How it's work ?
We use a mobile app made with kotlin, and with the help of the [Vosk](https://alphacephei.com/vosk/android) and the french model of [Paul Guyot](https://alphacephei.com/vosk/models) to translate your speech to text.
To detecte the intention and understande what you want we use RASA as NLU and Spacy as NLP.

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

## Intent understand

| Intent | Server | App | Descritption|
| :------| :----- | :-- | :-----------|
| open| OK | NO | open an application on the phone |

