package org.minima.tests.objects;

import org.minima.objects.base.MiniData;
import org.minima.objects.base.MiniString;
import org.minima.objects.proofs.ScriptProof;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class ScriptProofTests {

    @Test
    public void testScriptProof() {
        String str = "test-string";
        // MiniData proofValue = new MiniData("#ffffffffffffffffffffffffff");
        MiniData j = new MiniData("#ED300A3D6C12BCFC24BB919C1F15E07F26A3A0C0");
        MiniData n = new MiniData("#FFFF");
        String proofString = j.toString();
        ScriptProof sp;
        ScriptProof sp2;
        try {
            sp = new ScriptProof(str, 160);
            // sp2 = new ScriptProof(str, str);
            System.out.println("ScriptProof value json - " + sp.toJSON());
            System.out.println("ScriptProof value json - " + sp.getScript());
            // System.out.println("ScriptProof value json - " + sp2.toJSON());

        } catch (Exception e) {
            System.out.println("Exception: " + e.toString() + " msg=" + e.getMessage());
            assertTrue(" there should not be an Exception", false);
        }

    }

    @Test
    public void testStaticReadAndWriteDataStream() {
        try {
            String str = "test-string";

            String proofValue = "ffff";
            ScriptProof sp;
            // ScriptProof sp2;

            sp = new ScriptProof(str, 160);
            // sp2 = new ScriptProof(str, proofValue);
            System.out.println("ScriptProof value json - " + sp.toJSON());
            System.out.println("ScriptProof value json - " + sp.getScript());
            // System.out.println("ScriptProof value json - " + sp2.toJSON());

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            sp.writeDataStream(dos);

            InputStream inputStream = new ByteArrayInputStream(bos.toByteArray());
            DataInputStream dis = new DataInputStream(inputStream);
            sp.ReadFromStream(dis);
            System.out.println("Proof json values in read and write stream - " + sp.toJSON());

            assertNotNull(sp);
        } catch (Exception e) {
            System.out.println("IOException: " + e.toString() + " msg=" + e.getMessage());
            assertTrue(" there should not be an IOException", false);
        }
    }
}
