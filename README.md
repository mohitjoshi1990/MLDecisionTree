# MLDecisionTree
Decision Tree For Supervised Learning

Implement the decision tree learning algorithm. As dis-
cussed in class, the main step in decision tree learning is choosing the
next attribute to split on. Implement the following two heuristics for
selecting the next attribute.
1. Information gain heuristic (See Class slides, Mitchell Chapter 3).
2. Variance impurity heuristic described below.
Let K denote the number of examples in the training set. Let K0
denote the number of training examples that have class = 0 and
K1 denote the number of training examples that have class = 1.
The variance impurity of the training set S is dened as:
V I(S) =   K0  K1
           K   K
Notice that the impurity is 0 when the data is pure. The gain for
this impurity is dened as usual.
Gain(S;X) = V I(S) - Pr(x)V I(Sx)

where X is an attribute, Sx denotes the set of training examples
that have X = x and Pr(x) is the fraction of the training examples
that have X = x (i.e., the number of training examples that have
X = x divided by the number of training examples in S).
 (40 points) Implement reduced error post pruning algorithm on the
tree from Question 1. The steps in the algorithm are given below. (See
also Mitchell, Chapter 3).
2
1. Consider each node for pruning.
2. Pruning = removing the subtree at that node, make it a leaf and
assign the most common class at that node.
3. A node is removed if the resulting tree performs no worse then the
original on the validation set.
4. Nodes are removed iteratively choosing the node whose removal
most increases the decision tree accuracy on the graph.
5. Pruning continues until further pruning is harmful.
Your implementations should be called from the command
line to get complete credit. Points will be deducted if it is not
possible to run all the dierent versions of your implementa-
tion from the command line.
 Implement a function to print the decision tree to standard output. We
will use the following format.
wesley = 0 :
| honor = 0 :
| | barclay = 0 : 1
| | barclay = 1 : 0
| honor = 1 :
| | tea = 0 : 0
| | tea = 1 : 1
wesley = 1 : 0
According to this tree, if wesley = 0 and honor = 0 and barclay = 0,
then the class value of the corresponding instance should be 1. In other
words, the value appearing before a colon is an attribute value, and the
value appearing after a colon is a class value.
 Once we compile your code, we should be able to run it from the
command line. Your program should take as input the following ve
arguments:
.\program <training-set> <validation-set> <test-set> <to-print>
to-print:{yes,no} <prune> prune:{yes, no}
3
It should output the accuracies on the test set for decision trees con-
structed using the two heuristics. If to-print equals yes, it should print
the decision tree in the format described above to the standard output.
 A README.txt le with detailed instructions on compiling the code.
 A document containing the accuracy on the training, validation, and
test set in both datasets for decision trees constructed using the two
heuristics mentioned above, with and without pruning.
