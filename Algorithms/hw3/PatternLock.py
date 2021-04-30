from itertools import permutations
import math
from draw import draw_path
from test import test_path

class password():
    def __init__(self, rule):
        self.rule = rule
        # Longest distance
        self.longest_length = 0.0
        # List of longest path. The longest path is not unique. 
        self.longest_path = []

    # Find the longest path
    def find_longest_path(self):
        coordinates = {1: (0,0), 2: (1,0), 3: (2,0), 4: (0,1), 5: (1,1), 6: (2,1), 7: (0,2), 8: (1,2), 9: (2,2)}
        goodPerms = []

        if self.rule == 1:
        #possible lengths 
            graphPerms = permutations("123456789")
            for i in graphPerms:
                i = (''.join(i))
                if "13" not in i and "71" not in i and "17" not in i and "93" not in i and "39" not in i and "28" not in i and "82" not in i and "46" not in i and "79" not in i and "31" not in i and "64" not in i and "97" not in i and "37" not in i and "73" not in i and "19" not in i and "91" not in i:
                    goodPerms.append(i)
  
            #find the longest distance of these lengths:
            for i in goodPerms:
                newLength = 0
                for j in (range(0, 8)):
                    newDist = self.distance((coordinates[int(i[j])]), (coordinates[int(i[j+1])]))
                    newLength = newLength + newDist
                if newLength > self.longest_length:
                    self.longest_length = newLength  
                    self.longest_path = []

                if newLength == self.longest_length:
                    self.longest_path.append(i)

        if self.rule == 2:
            graphPerms = permutations("123456789")
            for i in graphPerms:
                i = (''.join(i))

                if "13" in i:                   
                    indexOf13 = i.find("13")
                    indexOf2 = i.find("2")
                    if indexOf2 < indexOf13:
                        goodPerms.append(i)
                elif "31" in i:
                    indexOf31 = i.find("31")
                    indexOf2 = i.find("2")
                    if indexOf2 < indexOf31:
                        goodPerms.append(i)
                elif "46" in i:
                    indexOf46 = i.find("46")
                    indexOf5 = i.find("5")
                    if indexOf5 < indexOf46:
                        goodPerms.append(i)
                elif "64" in i:
                    indexOf64 = i.find("64")
                    indexOf5 = i.find("5")
                    if indexOf5 < indexOf64:
                        goodPerms.append(i)
                elif "79" in i:
                    indexOf79 = i.find("79")
                    indexOf8 = i.find("8")
                    if indexOf8 < indexOf79:
                        goodPerms.append(i)
                elif "97" in i:
                    indexOf97 = i.find("97")
                    indexOf8 = i.find("8")
                    if indexOf8 < indexOf97:
                        goodPerms.append(i)
                elif "71" in i:
                    indexOf71 = i.find("71")
                    indexOf4 = i.find("4")
                    if indexOf4 < indexOf71:
                        goodPerms.append(i)
                elif "17" in i:
                    indexOf17 = i.find("17")
                    indexOf4 = i.find("4")
                    if indexOf4 < indexOf17:
                        goodPerms.append(i)
                elif "82" in i:
                    indexOf82 = i.find("82")
                    indexOf5 = i.find("5")
                    if indexOf5 < indexOf82:
                        goodPerms.append(i)
                elif "28" in i:
                    indexOf28 = i.find("28")
                    indexOf5 = i.find("5")
                    if indexOf5 < indexOf28:
                        goodPerms.append(i)
                elif "93" in i:
                    indexOf93 = i.find("93")
                    indexOf5 = i.find("5")
                    if indexOf5 < indexOf93:
                        goodPerms.append(i)
                elif "39" in i:
                    indexOf39 = i.find("39")
                    indexOf5 = i.find("5")
                    if indexOf5 < indexOf39:
                        goodPerms.append(i)
                elif "91" in i:
                    indexOf91 = i.find("91")
                    indexOf5 = i.find("5")
                    if indexOf5 < indexOf91:
                        goodPerms.append(i)
                elif "19" in i:
                    indexOf19 = i.find("19")
                    indexOf5 = i.find("5")
                    if indexOf5 < indexOf19:
                        goodPerms.append(i)
                elif "73" in i:
                    indexOf73 = i.find("73")
                    indexOf5 = i.find("5")
                    if indexOf5 < indexOf73:
                        goodPerms.append(i)
                elif "37" in i:
                    indexOf37 = i.find("37")
                    indexOf5 = i.find("5")
                    if indexOf5 < indexOf37:
                        goodPerms.append(i)
                else: goodPerms.append(i)
  
            #find the longest distance of these lengths:
            for i in goodPerms:
                newLength = 0
                for j in (range(0, 8)):
                    newDist = self.distance((coordinates[int(i[j])]), (coordinates[int(i[j+1])]))
                    newLength = newLength + newDist
                if newLength > self.longest_length:
                    self.longest_length = newLength  
                    self.longest_path = []

                if newLength == self.longest_length:
                    self.longest_path.append(i)

    # Calculate distance between two vertices
    # Format of a coordinate is a tuple (x_value, y_value), for example, (1,2), (0,1)
    def distance(self, vertex1, vertex2):
        return math.sqrt((vertex1[0]-vertex2[0])**2 + (vertex1[1]-vertex2[1])**2)

    def print_result(self):
        print("The longest length using rule " + str(self.rule) + " is:")
        print(self.longest_length)
        print()
        print("All paths with longest length using rule " + str(self.rule) + " are:") 
        print(self.longest_path)
        print()
        with open('results_rule'+str(self.rule)+'.txt', 'w') as file_handler:
            file_handler.write("{}\n".format(self.longest_length)) 
            for path in self.longest_path:
                file_handler.write("{}\n".format(path)) 

    # test the result 
    def test(self):
        test_path(self.longest_length, self.longest_path, self.rule)

    # draw first result
    def draw(self):
        if len(self.longest_path) > 0:
            draw_path(self.longest_path[0], self.rule)

if __name__ == "__main__":

    for rule in range(1,3):
        # Initialize the object using rule 1 or rule 2
        run = password(rule)
        # Find the longest path
        run.find_longest_path()
        # Print and save the result
        run.print_result()
        # Draw the first longest path
        run.draw()
        # Verify the result 
        run.test()