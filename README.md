# PingCoin

## Date 15/02/2018

### Changes:
1. CLI class is added. Console application development.
2. Main class is coded so that it'll act like a console application.
3. Block class is optimized.

### Implementation:
1. Able to export the project as runnable jar that run as console application. (java -jar "path-to-file" -help/-getblock)

## Date 12/02/2018

### Changes:
1. TestingClass class is added.
2. Blockchain class will manage chain of blocks
3. blockMasterElection class make use of Blockchain class instead of ArrayList<Block>.
4. Block class make use of Blockchain class to get height of the block
5. Cleaned Main class for console application development

### Implementation:
1. Changes in usage of Blockchain class

## Date 10/02/2018

### Changes:
1. Transaction class is added.
2. TransactionIn class is added.
3. TransactionOut class is added.

### Implementation:
1. TestTransaction class has testing code
2. PublicPrivateKey is created, transaction is made, transaction is verified using *whose* private key.

## Date 08/02/2018

### Changes: 
1. Replace simple storage method to database storage method. 
2. Reading blocks from database storage
### Implementation:
1. Stored the blockchain(Genesis block and 1st created block from the Genesis) into a database file blockchainDb.db.

## Date 07/02/2017

### Changes:
1. Added class markleTree to calculate markle root.
  
### Implementation:
1. Not implemented yet because need transactions first.

## Date 07/02/2017

### Changes:
1. Added class Blockchain and Database to store the blockchain.

### Implementation:
1. Stored the blockchain(Genesis block and 1st created block from the Genesis) into a file blockchain.dta.

## Date 05/02/2017

### Changes:
1. Added a class to generate public and private key.
  
### Implementation:
1. The is no implementation because to implament that we need to create a valid transaction first.
