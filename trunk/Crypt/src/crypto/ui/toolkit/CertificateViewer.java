package crypto.ui.toolkit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.TBSCertificateStructure;
import org.bouncycastle.asn1.x509.X509CertificateStructure;

import crypto.common.BasicMethod;

public class CertificateViewer extends JFrame {
	static public final long serialVersionUID = 2;

	JFrame parent;
	JTextArea output;
	JPanel subPanel;
	JButton backButton, finishButton;
	JList list;
	Vector<String> listData = new Vector<String>();

	public CertificateViewer() {
		super("Certificate Viewer");
		parent = this;
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
		output = new JTextArea();
		output.setAutoscrolls(true);
		output.setEditable(false);
		output.setLineWrap(true);
		output.setText(crypto.common.BasicMethod.readFile("report.txt"));
		output.setToolTipText("crypto execution result");
		output.setSize(400, 200);
		output.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Execution result"), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));

		list = new JList();
		list.setAutoscrolls(true);
		list.setSize(400, 200);
		try {
			adder();
		} catch (Exception e) {
		}
		list.setListData(listData);

		backButton = new JButton("Prev");
		backButton.setDisplayedMnemonicIndex(0);
		finishButton = new JButton("Finish");
		finishButton.setDisplayedMnemonicIndex(0);

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				parent.dispose();
			}
		});

		finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int n = JOptionPane.showConfirmDialog(parent,
						"Are you sure to finish?", "Crypto",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (n == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});

		subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(1, 2));
		subPanel.add(backButton);
		subPanel.add(finishButton);

		setLayout(new BorderLayout());
		// add(output, BorderLayout.CENTER);
		add(list, BorderLayout.CENTER);
		add(subPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		Toolkit theKit = this.getToolkit();
		Dimension wndSize = theKit.getScreenSize();
		setBounds(wndSize.width / 4, wndSize.height / 4, wndSize.width / 2,
				wndSize.height / 2);
		setVisible(true);
	}

	public void adder() throws Exception {
		BufferedInputStream in = null;
		String infile = "D://Document//Document//ACM班//Crypt//EC2007//certificate-Lai.cer";
		try {
			in = new BufferedInputStream(new FileInputStream(infile));
		} catch (FileNotFoundException fnf) {
			BasicMethod.error("Input file not found [" + infile + "]");
			System.exit(1);
		}

		ASN1InputStream aIn;
		aIn = new ASN1InputStream(in);
		ASN1Sequence seq = (ASN1Sequence) aIn.readObject();
		X509CertificateStructure obj = new X509CertificateStructure(seq);
		TBSCertificateStructure tbs = obj.getTBSCertificate();
		listData.add("颁发者" + tbs.getIssuer());
		listData.add("有效起始日期" + tbs.getStartDate());
		listData.add("有效终止日期" + tbs.getEndDate());
		listData.add("版本V" + tbs.getVersion());
		listData.add("序列号" + tbs.getSerialNumber());
		listData.add("签名人ID" + tbs.getIssuerUniqueId());
		listData.add("主题ID" + tbs.getSubjectUniqueId());
		listData.add("公钥ID"
				+ tbs.getSubjectPublicKeyInfo().getAlgorithmId().getObjectId());
		listData.add("公钥" + tbs.getSubjectPublicKeyInfo().getPublicKey());
		listData.add("公钥数据" + tbs.getSubjectPublicKeyInfo().getPublicKeyData());
		listData
				.add("公钥数据长度"
						+ tbs.getSubjectPublicKeyInfo().getPublicKeyData()
								.getBytes().length);
		listData.add("主题" + tbs.getSubject());
		listData.add("附加" + tbs.getExtensions().getDERObject());
		listData.add("签名ID" + tbs.getSignature().getObjectId());
		listData.add("签名参数" + tbs.getSignature().getParameters());

		listData.add("算法ID" + obj.getSignatureAlgorithm().getObjectId());
		listData.add("签名" + obj.getSignature());

		String s = obj.getSubjectPublicKeyInfo().getPublicKey().toString();
		s = s.substring(1, s.length() - 1);
		BigInteger pubExp = new BigInteger(s.substring(s.indexOf(",") + 2), 10);
		listData.add(pubExp.toString());
		BigInteger mod = new BigInteger(s.substring(0, s.indexOf(",")), 10);
		listData.add(String.valueOf(mod.bitLength()));
	}

	public static void main(String[] args) {
		new CertificateViewer();
	}
}
