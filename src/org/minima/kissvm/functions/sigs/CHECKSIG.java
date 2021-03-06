package org.minima.kissvm.functions.sigs;

import org.minima.kissvm.Contract;
import org.minima.kissvm.exceptions.ExecutionException;
import org.minima.kissvm.functions.MinimaFunction;
import org.minima.kissvm.values.BooleanValue;
import org.minima.kissvm.values.HEXValue;
import org.minima.kissvm.values.Value;
import org.minima.objects.base.MiniData;
import org.minima.objects.keys.MultiKey;

/**
 * for now only retur  true..
 * 
 * @author spartacusrex
 *
 */
public class CHECKSIG extends MinimaFunction {

	public CHECKSIG() {
		super("CHECKSIG");
	}
	
	@Override
	public Value runFunction(Contract zContract) throws ExecutionException {
		
		//Get the Pbkey
		HEXValue pubkey = (HEXValue) getParameter(0).getValue(zContract);
		
		//get the data
		HEXValue data   = (HEXValue) getParameter(1).getValue(zContract);
		
		//Get the signature
		HEXValue sig    = (HEXValue) getParameter(2).getValue(zContract);
		
		//Check it..
		MiniData pubk = new MiniData(pubkey.getMiniData().getData());
		
		//Create a MultiKey to check the signature
		MultiKey checker = new MultiKey();
		checker.setPublicKey(pubk);
		
		//Check it..
//		boolean ok = PubPrivKey.verify(leafkey, transhash, signature);
		boolean ok = checker.verify(new MiniData(data.getRawData()), sig.getMiniData());
		
//		boolean ok = PubPrivKey.verify(pubk, new MiniData(data.getRawData()), sig.getMiniData());
		
		// TODO Auto-generated method stub
		return new BooleanValue(ok);
	}
	
	@Override
	public MinimaFunction getNewFunction() {
		return new CHECKSIG();
	}

}
