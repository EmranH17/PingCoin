import java.security.*;
import java.util.ArrayList;

public class Transaction {

	public String transactionId; // this is also the hash of the transaction.
	public PublicKey sender; // senders address/public key.
	public PublicKey reciepient; // Recipients address/public key.
	public float value;
	public byte[] signature; // this is to prevent anybody else from spending funds in our wallet.
	
	public ArrayList<TransactionIn> inputs = new ArrayList<TransactionIn>();
	public ArrayList<TransactionOut> outputs = new ArrayList<TransactionOut>();
	
	private static int sequence = 0; // a rough count of how many transactions have been generated. 
	
	// Constructor: 
	public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionIn> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
	}
	
	// This Calculates the transaction hash (which will be used as its Id)
	private String calulateHash() {
		sequence++; //increase the sequence to avoid 2 identical transactions having the same hash
		return HashFunctionUtility.applySha256( HashFunctionUtility.getStringFromKey(sender) + HashFunctionUtility.getStringFromKey(reciepient) + Float.toString(value) + sequence);
}
	
	//Signs all the data we do not wish to be tampered with.
	public byte[] generateSignature(PrivateKey privateKey) {
		String data = HashFunctionUtility.getStringFromKey(sender) + HashFunctionUtility.getStringFromKey(reciepient) + Float.toString(value)	;
		return signature = HashFunctionUtility.applyECDSASig(privateKey,data);		
	}
	//Verifies the data we signed has not been tampered with
	public boolean verifiySignature() {
		String data = HashFunctionUtility.getStringFromKey(sender) + HashFunctionUtility.getStringFromKey(reciepient) + Float.toString(value)	;
		return HashFunctionUtility.verifyECDSASig(sender, data, signature);
	}
}
