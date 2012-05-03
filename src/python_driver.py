import sys

class ParserDriver:

    def __init__(self, parse_table):
        self.nonterminals = []
        self.tokens = []
        self.parse_table = self.genParseTable(parse_table)



    def genParseTable(self, parse_table):

        lines = open(parse_table).readlines()
        lines[-1] += ","

        cells = [ [y for y in x.upper().rstrip("\n").split(",")] for x in lines  ]

        self.tokens = cells.pop(0)

        parse_table = dict()

        # Set each item of the parse table, indexed to (nonterminal, terminal), to the value of that cell
        [ parse_table.__setitem__((row[0].upper(), term.upper()),row[self.tokens.index(term)].split(" ")) for row in cells for term in self.tokens]

        # Add all the keys in the parse table to the non-terminals list
        [ self.nonterminals.append(x[0]) for x in parse_table.keys() ]

        return parse_table

    def parseFile(self, infile):
        input_string = open(infile).read()

        # Initialize the input stack.
        input_stack = [ x for x in input_string.split() if x.lower() in [ y.lower() for y in self.tokens]]


        # Set the first value of parse_stack to the start state, manually done here.

        ## parse_stack = ["<Tiny-program>".upper()]
        parse_stack = ["<exp>".upper()]

        # Run the parser
        while len(parse_stack) > 0:
            print input_stack, parse_stack

            # If the input is the expected terminal
            if input_stack[0] == parse_stack[0]:
                print "Match. Pop from iStack: {}, pStack: {}".format(input_stack[0], parse_stack[0])
                input_stack.pop(0);
                parse_stack.pop(0);

            else: # The parse_stack was a nonterminal, expand it as is appropriate

                parse_stack_pop = parse_stack.pop(0)

                rule = self.parse_table.get((parse_stack_pop, input_stack[0]))
                print "Table[{},{}] = Rule: {}".format(parse_stack_pop,input_stack[0],rule)

                if rule:
                    #print ("I" +  str(parse_stack ))
                    [ parse_stack.insert(0,str(x).upper()) for x in reversed(rule) ]
                    #print ("O" + str(parse_stack ))
                else: # Empty cell, shouldn't be here.
                    print "Broken -- not expecting {}.\n Input stack: {}\n Parse stack: {}\n".format(input_stack[0], input_stack, parse_stack)
                    break;

            print " Input stack: {}\n Parse stack: {}\n".format(input_stack, parse_stack)


def main():
    parse_table = sys.argv[1]
    token_file = sys.argv[2]

    driver = ParserDriver(parse_table)
    driver.parseFile(token_file)

if __name__ == "__main__":
    main()
