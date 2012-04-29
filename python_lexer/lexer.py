import sys,os,re
#spth = os.path.abspath("{0}{1}language_specifications".format(os.getcwd(), os.sep))
#sys.path.insert(0,spth)
#from helper_functions import *
#from token_spec import *

tokens = dict( [x for x in {
    # Terminals
    "LEFTPAR"     : "(?ms)" + re.escape("("),
    "RIGHTPAR"    : "(?ms)" + re.escape(")"),
    "ASSIGN"      : "(?ms)" + re.escape(":="),
    "COMMA"       : "(?ms)" + re.escape(","),
    "SEMICOLON"   : "(?ms)" + re.escape(";"),
    "PLUS"        : "(?ms)" + re.escape("+"),
    "MINUS"       : "(?ms)" + re.escape("-"),
    "MULTIPLY"    : "(?ms)" + re.escape("*"),
    "MODULO"      : "(?ms)" + re.escape("%"),

    # Keywords
    "BEGIN"       : "(?ms)" + re.escape("BEGIN"),
    "END"         : "(?ms)" + re.escape("END"),
    "PRINT"       : "(?ms)" + re.escape("PRINT"),

    # Non-implemented
    "LEFTBRA"     : "(?ms)" + re.escape("{"),
    "RIGHTBRA"    : "(?ms)" + re.escape("}"),
    "READ"        : "(?ms)" + re.escape("READ"),
    "IF"          : "(?ms)" + re.escape("IF"),
    "THEN"        : "(?ms)" + re.escape("THEN"),
    "UNTIL"       : "(?ms)" + re.escape("UNTIL"),
    "LESSTHAN"    : "(?ms)" + re.escape("<"),
    "GREATERTHAN" : "(?ms)" + re.escape(">"),
    "REPEAT"      : "(?ms)" + re.escape("REPEAT"),
    "EQUALTO"     : "(?ms)" + re.escape("="),



    "INTNUM"      : "0|[1-9][0-9]*",
    "ID"          : "[_A-Za-z][_A-Za-z0-9]{,9}",


}.iteritems()])

def arr_to_rexp(arr):
    arr = [ "{0}|".format(x.strip()) for x in arr]
    return "(" + arr_to_str( arr, sep="") + ")"
def arr_to_str(arr, sep=" "):
    return reduce(lambda x,y: x+sep+y, [""] + arr).strip()
def usage():
    return "\nUsage: python " + sys.argv[0] + " [input_file.tny] [output_file.tok]"

if len(sys.argv) < 3:
    sys.stderr.write("Missing argument." + usage() + "\n")
    exit(1)
try:
    in_file = open(sys.argv[1]).read()
except IOError:
    sys.stderr.write("Something is messed up with that file. Try again.\n")
    exit(1)

try:
    out_file = open(sys.argv[2], 'w')
except IOError:
    sys.stderr.write("Something is borked, cannot open {0} with write access.\n".format(sys.argv[2]))
    exit(1)

sstr = arr_to_rexp(tokens.values())

split_input = [y.strip() for x in re.split( sstr, in_file) for y in x.split()]

output = []

comment = False
for x in split_input:
    if comment or re.match(tokens["LEFTBRA"], x):
        comment = True
        if not re.match(tokens["RIGHTBRA"],x):
            continue
        comment = False
        continue

    tok = x

    for y in tokens.items():
        match = re.match(y[1], tok, flags=re.I)
        if match:
            tok = y[0]
            break
        #tok = re.sub(y[1], y[0], tok, flags=re.I)
    output += [(x,tok)]

output = [ (x.strip(),y.strip()) for (x,y) in output ]

out_file

for x in output:
    print "{0: >8} : {1}".format(x[0],x[1])

ostr = ""
for x,y in output:
    #out_file.write(x + " ")
    ostr += (x + " ")
#out_file.write("\n")
ostr += "\n"
for x,y in output:
    out_file.write(y + " ")
    ostr += (y + " ")
out_file.write("\n")
#print ostr
out_file.close()
