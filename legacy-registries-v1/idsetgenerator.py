# leaving this here in case someone finds it useful
# paste all the public static final stuff and it will giv u the setter code
# imagine choosing each id manually am I right
# take that mine diver

import re

def load(filename):
    with open(filename) as file:
        return file.read()

data = load("input.txt").split("\n")

test = data[0].strip().split(" ")
mode = 3 # non final naming conventions
if test[2] == "final":
  mode = 4 # final naming conventions

cameltosnake = re.compile(r'(?<!^)(?=[A-Z])') # I wanted an elegant 1 liner so found this nice regex on stack overflow. It checks it's not at the beginning and is capital letter afterwards.
convert = (lambda name : name.lower()) if mode == 4 else (lambda name : cameltosnake.sub('_', name).lower())

data = map(lambda line : line.strip().split(" ", 5)[mode], data)

for item in data:
    if item.endswith(";"):
        item = item[:-1]
    
    print("set(" + item + ", \"" + convert(item) + "\");")

input()
