import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

// The bloom filter implementation
public class BloomFilter {

    private final MessageDigest SHA1MessageDigest;					  // The hashing technique
    private BitSet m;											      // The filter with all the users
    private BitSet currM;											  // The filter with only the current user 
    private int k;											          // The number of bits from the hashing

	// The initialization of the bloom filter parameters
    public BloomFilter() throws NoSuchAlgorithmException {
        m 	  = new BitSet(Constants.M_FILTER_SIZE);
        currM = new BitSet(Constants.M_FILTER_SIZE);
        
        // Optimal number of bits that marked as mention in the document
        k =  (int) ((Constants.M_FILTER_SIZE / Constants.N_NUMBER_OF_USERS) * Math.log(2));
        SHA1MessageDigest = java.security.MessageDigest.getInstance("SHA-1");
    }
    
    // Create hashing according the user name that finally gives k hashing.
    private int[] Hash(byte[] UserName) {
        int currk = 0;						// How many indexes have received since far from the hash 
        byte SaltNumber = 0;				// The salt that added to the hashing
        int RunningNumber = 0;				// Sequential number that added to the salt for more versatility 
        int[] FinalIndexes = new int[k];	// All the indexes, that get from hashing  
        byte[] hash;						// The array that contain the digested user name with salt in bits
        int BitIndex;						// The array for hash bit manipulating

        // Getting all the indexes that give places in the array 
        while (currk < k) {
        	
        	// Create salt number and add it to the hashing
            SaltNumber = (byte)(RunningNumber);
        	SHA1MessageDigest.update(SaltNumber);
        	hash = SHA1MessageDigest.digest(UserName);                
        	RunningNumber++;

        	// Divide the hashing process into four bytes block because the process is too demanding for the whole segment
            for (int i = 0; i < hash.length/4 && currk < k; i++) {
                BitIndex = 0;
                for (int j = 4*i; j < 4*i+4; j++) {
                	BitIndex <<= 8;
                	BitIndex |= ((int) hash[j]) & 0xFF;
                }
                FinalIndexes[currk] = BitIndex;
                currk++;
            }
        }
        return FinalIndexes;
    }

    // Getting the the index of the bit in the bloom filter that received from the hashing 
    private int getBitIndex(int hash) {
        return Math.abs(hash % Constants.M_FILTER_SIZE);
    }
    
    // Convert current user name from string to bytes and add it to the current bloom filter
    public void CurrUserNameCheck(String user) {
    	CurrUserNameCheck(user.toString().getBytes());
    }

    // calculate the current user location in the filter before add it to the users list
    private void CurrUserNameCheck(byte[] bytes) {
       int[] hashes = Hash(bytes);
       for (int i = 0; i < Constants.M_FILTER_SIZE; i++)
    	   currM.set(i, false);
       for (int hash : hashes)
    	   currM.set(getBitIndex(hash), true);
    }

    // Convert user name from string to bytes and add it to the bloom filter
    public void add(String user) {
       add(user.toString().getBytes());
    }

    // Add user to the bloom filter
    private void add(byte[] bytes) {
       int[] hashes = Hash(bytes);
       for (int hash : hashes)
           m.set(getBitIndex(hash), true);
    }
    
    // Convert user name from string to bytes and and check if it in the filter
    public boolean contains(String user) {
        return contains(user.toString().getBytes());
    }

    // Return false if the user is not exists in the array or yes if he may exists
    private boolean contains(byte[] bytes) {
        for (int hash : Hash(bytes)) {
            if (!m.get(getBitIndex(hash))) {
                return false;
            }
        }
        return true;
    }

    // Return the array of all the the users
	public BitSet getAllUsersBloomArray() {
		return m;
	}

    // Return the array of all the the users including the current one(the one that been checked)
    public BitSet getCurrentUserBloomArray() {
		return currM;
	}
    
    // clear the set and the bloom filter
    public void clear() {
        m.clear();
    }
}