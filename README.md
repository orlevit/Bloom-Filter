# Bloom Filter

The idea was invented by Burton Howard in 1970, a computer science graduate at MIT who conceived the idea while working at a computer use company.\
It is a method that designed to find element among group of elements. it does not keep the elements themselves in the search structure but their hash.\
Contrary to other findings methods that have an one-to-one correspondence between negative answer **<->** the element didn't found in the group and positive answer **<->** the element found in ther group.\
In Bloom filter: negative answer **<->** the element didn't found in the group and positive answer **<->** the element _**might**_ found in ther group.\
In other words, smaller space occupation was obtain rather than better accuracy, Bloom's technique uses a smaller hash area but still eliminates most unnecessary accesses, For instance, a hash area only 15% of the size needed by an ideal error-free hash still eliminates 85% of the disk accesses.
