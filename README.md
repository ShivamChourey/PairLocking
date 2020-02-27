# PairLocking
MPP Project


This project proposes a new locking mechanism inspired by the hand-over-hand locking method used in fine-grained locking termed as ’pair locking’. There are some issues in the hand-over-hand locking mechanism; traversal of linked lists and skiplists in hand-over-hand locking hits a pause if a thread is unable to acquire the lock on the current (successor) node after it locks the predecessor node, while remove() operations require that the predecessor node as well as the current node be locked. This proposed ‘pair lock’ scheme attempts to make threads acquire locks on two consecutive nodes at the same time. In this case, a thread will progress by acquiring locks on both the predecessor node and current node at once.
