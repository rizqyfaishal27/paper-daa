import re

text = open('sample.txt', 'rb')
output = open('output.txt', 'w')
for text_x in text.readlines():
	text_x = text_x.lower()
	text_x = re.sub(r'[\W]', '', text_x)
	text_x = re.sub(r'[\n\r]', '', text_x)
	text_x = re.sub(r'[0-9_]', '', text_x)
	output.write(text_x)