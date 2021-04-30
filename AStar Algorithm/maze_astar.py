import numpy as np
from heapq import heappush, heappop
from animation import draw
import argparse
import math

class Node():
    """
    cost_from_start - the cost of reaching this node from the starting node
    state - the state (row,col)
    parent - the parent node of this node, default as None
    """
    def __init__(self, state, cost_from_start, parent = None):
        self.state = state
        self.parent = parent
        self.cost_from_start = cost_from_start


class Maze():
    
    def __init__(self, map, start_state, goal_state, map_index):
        self.start_state = start_state
        self.goal_state = goal_state
        self.map = map
        self.visited = [] # state
        self.m, self.n = map.shape 
        self.map_index = map_index


    def draw(self, node):
        path=[]
        while node.parent:
            path.append(node.state)
            node = node.parent
        path.append(self.start_state)
    
        draw(self.map, path[::-1], self.map_index)


    def goal_test(self, current_state):
        return current_state == self.goal_state


    def get_cost(self, current_state, next_state):
        return 1


    def get_successors(self, state):
        successors = []
        #where 0.5 currently is
        r, c = state
        rowLen = len(self.map)
        colLen = len(self.map[0])
        map_copy = self.map.copy()

        #top
        if r-1 >= 0 and map_copy[r - 1, c] > 0:
            successors.append((r-1, c))
        #bottom
        if r + 1 < rowLen and map_copy[r+1, c] > 0:
            successors.append((r+1, c))
        #left
        if c - 1 >= 0 and map_copy[r, c-1] > 0:
            successors.append((r, c-1))
            
        #right
        if c + 1 < colLen and map_copy[r, c+1] > 0:
            successors.append((r, c+1))

        return successors


    # heuristics function
    def heuristics(self, state):
        a, b = state
        c, d = self.goal_state

        e = (c-a)
        f = (d-b)

        return abs(e) + abs(f)

    # priority of node 
    def priority(self, node):
        return node.cost_from_start + self.heuristics(node.state)

    
    # solve it
    def solve(self):
        fringe = []
        count = 1
        state = self.start_state
        node = Node(state, 0, None)
       
        heappush(fringe, (node.cost_from_start, count, node))
        while fringe is not None:
            current = heappop(fringe)
            self.visited.append(current[2].state)
            if self.goal_test(current[2].state) == True:
                self.draw(current[2])
            else:
                successors = self.get_successors(current[2].state)
                for i in successors:
                    flag = False
                    for j in self.visited:
                        if i == j:
                            flag = True
                    if flag is False:
                        #def __init__(self, state, cost_from_start, parent = None):
                        newNode = Node(i, self.get_cost(i, current[2].state) + self.priority(current[2]), current[2])
                        heappush(fringe, (newNode.cost_from_start, count, newNode))
                        count += 1
            
    
if __name__ == "__main__":

    parser = argparse.ArgumentParser(description='maze')
    parser.add_argument('-index', dest='index', required = True, type = int)
    index = parser.parse_args().index

    # Example:
    # Run this in the terminal solving map 1
    #     python maze_astar.py -index 1
    
    data = np.load('map_'+str(index)+'.npz')
    map, start_state, goal_state = data['map'], tuple(data['start']), tuple(data['goal'])

    game = Maze(map, start_state, goal_state, index)
    game.solve()
    