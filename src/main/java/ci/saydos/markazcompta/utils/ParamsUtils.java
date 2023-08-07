package ci.saydos.markazcompta.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * BUSINESS factory
 *
 * @author Diomande Souleymane
 */
 
@Data
@Component
public class ParamsUtils {

@Value("${smtp.mail.adresse}")
private String smtpLogin;
//
@Value("${url.admin}")
private String urlAdmin;
//
@Value("${smtp.mail.host}")
private String smtpHost;
//
@Value("${smtp.mail.port}")
private Integer smtpPort;
//
@Value("${smtp.mail.adresse}")
private String smtpMailAdresse;
//
@Value("${smtp.mail.password}")
private String smtpPassword;
//
@Value("${api.sms.senderAddress}")
private String smsSenderAddress;
//
@Value("${api.sms.contentType.key}")
private String smsContentTypeKey;
//
@Value("${api.sms.contentType.value}")
private String smsContentTypeValue;
//
@Value("${api.sms.header.authorization}")
private String smsHeaderAuthorization;
//
@Value("${api.sms.bearer}")
private String smsBearer;
//
@Value("${api.sms.token}")
private String smsToken;
//
@Value("${api.sms.url}")
private String smsUrl;
//
@Value("${parametre.dossier}")
private String pathDossier;
//
@Value("${url.root}")
private String rootFilesUrl;
//
@Value("${image.directory}")
private String imageDirectory;
//
@Value("${textfile.directory}")
private String textfileDirectory;
//
@Value("${video.directory}")
private String videoDirectory;
//
@Value("${otherfile.directory}")
private String otherfileDirectory;
//
@Value("${url.root}")
private String urlRoot;
//
//
@Value("${minio.bucket.name}")
private String defaultBucketName;
//
@Value("${minio.default.folder}")
private String defaultBaseFolder;

	
	
	//Getter and Setter
	public String getSmtpLogin() {
		return smtpLogin;
	}

	public void setSmtpLogin(String smtpLogin) {
		this.smtpLogin = smtpLogin;
	}

	public String getUrlAdmin() {
		return urlAdmin;
	}

	public void setUrlAdmin(String urlAdmin) {
		this.urlAdmin = urlAdmin;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpMailAdresse() {
		return smtpMailAdresse;
	}

	public void setSmtpMailAdresse(String smtpMailAdresse) {
		this.smtpMailAdresse = smtpMailAdresse;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getSmsSenderAddress() {
		return smsSenderAddress;
	}

	public void setSmsSenderAddress(String smsSenderAddress) {
		this.smsSenderAddress = smsSenderAddress;
	}

	public String getSmsContentTypeKey() {
		return smsContentTypeKey;
	}

	public void setSmsContentTypeKey(String smsContentTypeKey) {
		this.smsContentTypeKey = smsContentTypeKey;
	}

	public String getSmsContentTypeValue() {
		return smsContentTypeValue;
	}

	public void setSmsContentTypeValue(String smsContentTypeValue) {
		this.smsContentTypeValue = smsContentTypeValue;
	}

	public String getSmsHeaderAuthorization() {
		return smsHeaderAuthorization;
	}

	public void setSmsHeaderAuthorization(String smsHeaderAuthorization) {
		this.smsHeaderAuthorization = smsHeaderAuthorization;
	}

	public String getSmsBearer() {
		return smsBearer;
	}

	public void setSmsBearer(String smsBearer) {
		this.smsBearer = smsBearer;
	}

	public String getSmsToken() {
		return smsToken;
	}

	public void setSmsToken(String smsToken) {
		this.smsToken = smsToken;
	}

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public String getPathDossier() {
		return pathDossier;
	}

	public void setPathDossier(String pathDossier) {
		this.pathDossier = pathDossier;
	}

	public String getRootFilesUrl() {
		return rootFilesUrl;
	}

	public void setRootFilesUrl(String rootFilesUrl) {
		this.rootFilesUrl = rootFilesUrl;
	}

	public String getDefaultBucketName() {
		return defaultBucketName;
	}

	public void setDefaultBucketName(String defaultBucketName) {
		this.defaultBucketName = defaultBucketName;
	}

	public String getDefaultBaseFolder() {
		return defaultBaseFolder;
	}

	public void setDefaultBaseFolder(String defaultBaseFolder) {
		this.defaultBaseFolder = defaultBaseFolder;
	}
	
	
	
	
	
	
	

}
