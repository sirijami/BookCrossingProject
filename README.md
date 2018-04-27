# BookCrossingProject
Dataset: Book-crossing dataset
http://www2.informatik.uni-freiburg.de/~cziegler/BX/

It includes 3 csv files:
BX-Users
Contains the users. Note that user IDs (`User-ID`) have been anonymized and map to integers. Demographic data is provided (`Location`, `Age`) if available. Otherwise, these fields contain NULL-values.
BX-Books
Books are identified by their respective ISBN. Invalid ISBNs have already been removed from the dataset. Moreover, some content-based information is given (`Book-Title`, `Book-Author`, `Year-Of-Publication`, `Publisher`), obtained from Amazon Web Services. Note that in case of several authors, only the first is provided. URLs linking to cover images are also given, appearing in three different flavours (`Image-URL-S`, `Image-URL-M`, `Image-URL-L`), i.e., small, medium, large. These URLs point to the Amazon web site.
BX-Book-Ratings
Contains the book rating information. Ratings (`Book-Rating`) are either explicit, expressed on a scale from 1-10 (higher values denoting higher appreciation), or implicit, expressed by 0.

Analysis performed on the above data set are:

Top ten best rated books.
Find list of books named by a author.
Find all the books purchased by a user.
Find list of users purchased a particular book.
Display unique list of books.
Display list of books purchased with age range from 35-45. 
Find list of books published in between 2004-2010.
Each user have bought how many books.
Frequency of book published each year.
Average rating of the books.
Display list of users aged between a range.
Sort books by highest rating.
Sort books by published year (Recent to older).
Best rated author via avg rating.
What other books can be recommended to users based on their interests. (Mahout)

Will be using 
Map/Reduce (HDFS)
HBase (For random read/writes)
Hive
Pig
Mahout 
