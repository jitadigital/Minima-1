package org.minima.database.txpowdb.java;

import java.util.Hashtable;

import org.minima.database.txpowdb.TxPOWDBRow;
import org.minima.objects.TxPoW;
import org.minima.objects.base.MiniData;
import org.minima.objects.base.MiniNumber;
import org.minima.utils.json.JSONObject;

public class JavaDBRow implements TxPOWDBRow {

	/**
	 * TxPoW data is stored in byte format and extracted when needed..
	 */
	private static Hashtable<String, MiniData> mTxPoWData = new Hashtable<>();
	public static void ClearTxPowData() {
		mTxPoWData.clear();
	}
	
	public static String AddTxPoWData(TxPoW zTxPOW) {
		//Get the ID	
		String id = zTxPOW.getTxPowID().to0xString();
		
		//Get the Data Array
		MiniData txdata = zTxPOW.createDataArray();
		
		//Store it..
		mTxPoWData.put(id, txdata);
		
		return id;
	}
	
	public static TxPoW GetTxPoWData(String zID) {
		MiniData data = mTxPoWData.get(zID);
		
		TxPoW txp = TxPoW.createTxPoW(data);
		
		return txp;
	}
	
	public static void RemoveTxPoWData(String zID) {
		mTxPoWData.remove(zID);
	}
	
	/**
	 * The Actual Data
	 */
	
	String mTxPowID;
	private TxPoW mTxPOW;

	private boolean mIsMainChainBlock;
	
	private boolean mIsInBlock;
	
	private MiniNumber mInBlocknumber;
	
	private int mBlockState;
	
	private long mDeleteTime;
	
	private long mAddedTime;
	
	private boolean mMonotonic;
	
	public JavaDBRow(TxPoW zTxPOW) {
		
//		mTxPowID = addTxPoWtoData(zTxPOW);
		mTxPOW 				= zTxPOW;
		
		mIsInBlock 			= false;
		mIsMainChainBlock     = false;
		mBlockState         = TXPOWDBROW_STATE_BASIC;
		mDeleteTime         = 0;
		mAddedTime          = System.currentTimeMillis();
		mInBlocknumber      = MiniNumber.ZERO;
		mMonotonic          = false;
	}

	@Override
	public JSONObject toJSON() {
		JSONObject ret = new JSONObject();
		
		ret.put("txpow",getTxPOW().toJSON());
		ret.put("isonchainblock",mIsMainChainBlock);
		ret.put("isinblock",mIsInBlock);
		ret.put("inblock",mInBlocknumber.toString());
		ret.put("blockstate",getStatusAsString());
		ret.put("monotonic",mMonotonic);
		ret.put("deleted",mDeleteTime);
		
		return ret;
	}
	
	@Override
	public TxPoW getTxPOW() {
		return mTxPOW;
//		return getTxPoWData(mTxPowID);
	}

	@Override
	public void setIsInBlock(boolean zIsInBlock) {
		mIsInBlock = zIsInBlock;
	}
	
	@Override
	public boolean isInBlock() {
		return mIsInBlock;
	}

	@Override
	public MiniNumber getInBlockNumber() {
		return mInBlocknumber;
	}

	@Override
	public void setInBlockNumber(MiniNumber zBlockNumber) {
		mInBlocknumber = zBlockNumber;
	}
	
	@Override
	public String toString() {
		return toJSON().toString();
	}

	public String getStatusAsString() {
		switch (mBlockState) {
		case TXPOWDBROW_STATE_BASIC:
			return "BASIC";
		case TXPOWDBROW_STATE_FULL:
			return "FULL";
		default:
			return "ERROR";
		}
	}
	
	@Override
	public boolean isMainChainBlock(){
		return mIsMainChainBlock;
	}
	
	@Override
	public void setMainChainBlock(boolean zMainChain) {
		mIsMainChainBlock = zMainChain;
	}

	@Override
	public int getBlockState() {
		return mBlockState;
	}

	@Override
	public void setBlockState(int zState) {
		mBlockState = zState;
	}

	@Override
	public void deleteRow() {
		if(mDeleteTime == 0) {
			mDeleteTime = System.currentTimeMillis();
		}
	}

	@Override
	public long getDeleteTime() {
		return mDeleteTime;
	}

	@Override
	public long getAddedTime() {
		return mAddedTime;
		
	}

	@Override
	public boolean isMonoTonic() {
		return mMonotonic;
	}

	@Override
	public void setMonotonic(boolean zMonotonic) {
		mMonotonic = zMonotonic;
	}
}
