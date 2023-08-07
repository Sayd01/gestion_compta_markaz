package ci.saydos.markazcompta.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import jakarta.activation.CommandMap;
import jakarta.activation.MailcapCommandMap;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import lombok.AllArgsConstructor;

import ci.saydos.markazcompta.utils.contract.Response;

/**
 * BUSINESS factory
 *
 * @author Diomande Souleymane
 */
 
@AllArgsConstructor
public class HostingUtils {

//	@Autowired
//	private TrptClientEsUtils trptClientEsUtils;
//
//	@Autowired
//	private HighClientFactory highClientFactory;
//
//	@Autowired
//	private EsConfig esConfig;
	
	private ParamsUtils paramsUtils;
	
	private TemplateEngine templateEngine;
	
	private ExceptionUtils exceptionUtils;

	private static final Logger slf4jLogger = LoggerFactory.getLogger(HostingUtils.class);

//	@Async
//	public void LogResponseTransaction(String RequestIdentifier, String RemoteAddr, String UserPrincipal,
//			String UserAgent, String Protocol, String SessionUser, String KeyAES, String CompanyID, String nom,
//			String prenom, String Uri, String HasError, String StatusCode, String StatusMessage, String RequestValue,
//			String ResponseValue, String Action, String code, String message, String ND, String Customer)
//			throws Exception {
//
//		// MDC.clear();
//		slf4jLogger.info(
//				" ------------------------  l'execution de la methode asynchrone    ----------------------------");
//
//		String connectedUserLogin = "";
//		String connectedUserFullName = "";
//		LogRefonteSiDto fDto = new LogRefonteSiDto();
////		new PubViewsDto();
//
//		if (SessionUser != null) {
//			// UserDto userDto = cacheUtils.get(SessionUser);
//			// if (userDto != null) {
//			// connectedUserLogin = userDto.getLogin();
//			// connectedUserFullName = userDto.getPrenom() + " " + userDto.getNom();
//			// }
//		}
//		fDto.setAction(Action);
//		fDto.setNd(ND);
//		fDto.setProtocol(Protocol);
//		fDto.setUserAgent(UserAgent);
//		fDto.setRemoteAddr(RemoteAddr);
//		fDto.setHasError(HasError);
//		fDto.setRequestValue(RequestValue);
//		fDto.setResponseValue(ResponseValue);
//		fDto.setRequestIdentifier(RequestIdentifier);
//		fDto.setConnectedUserLogin(connectedUserLogin);
//		fDto.setConnectedUserFullName(connectedUserFullName);
//		fDto.setLoggedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Utilities.getCurrentDate()));
//		fDto.setSessionUser(SessionUser);
//		fDto.setUri(Uri);
//		fDto.setNd(ND);
//		fDto.setStatusCode(code);
//		fDto.setStatusMessage(message);
//		fDto.setCustomer(Customer);
//		trptClientEsUtils.insert("my_nina-" + new SimpleDateFormat("yyyy.MM.dd").format(new Date()), "_doc",
//				"/templates/es/template_mynina.json", highClientFactory, 1024, fDto, RequestIdentifier);
//		// trptClientEsUtils.insert("log_refonte_si-"+"2020.05.20", "_doc",
//		// "/templates/es/template_refonte_si.json", highClientFactory, 1024,
//		// fDto,RequestIdentifier);
//		// used for es insertion
////		MDC.put("requestIdentifier", RequestIdentifier);
////		MDC.put("canShow", "false");
////		MDC.put("logType", "Response");
////		MDC.put("remoteAddr", RemoteAddr);
////		MDC.put("userPrincipal", UserPrincipal);
////		MDC.put("userAgent", UserAgent);
////		MDC.put("protocol", Protocol);
////		MDC.put("companyID", Utilities.notBlank(CompanyID) ? CompanyID : "admin");
////		MDC.put("uri", Uri);
////		MDC.put("hasError", HasError);
////		MDC.put("statusCode", StatusCode);
////		MDC.put("statusMessage", StatusMessage);
////		MDC.put("requestValue", RequestValue);
////		MDC.put("responseValue", ResponseValue);
////		MDC.put("sessionUser", SessionUser);
////		MDC.put("action", Action);
////		MDC.put("nd", ND);
////		MDC.put("customer", Customer);
////
////
////
////		MDC.put("connectedUserLogin", connectedUserLogin);
////		MDC.put("connectedUserFullName", connectedUserFullName);
//		slf4jLogger.info(
//				"Request:: RequestIdentifier:{},RemoteAddr:{} ,UserPrincipal:{},UserAgent:{},Protocol:{},SessionUser:{},KeyAES:{},CompanyID:{},FirstName:{},LastName:{},Uri:{},HasError:{},StatusCode:{},StatusMessage:{},RequestValue:{},ResponseValue:{},Action:{}",
//				RequestIdentifier, RemoteAddr, UserPrincipal, UserAgent, Protocol, SessionUser, KeyAES, CompanyID, nom,
//				prenom, Uri, HasError, StatusCode, StatusMessage, RequestValue, ResponseValue, Action);
//		// MDC.clear();
//	}
//
////	@Async
////	public void LogResponsePubViewN(String RequestIdentifier, Integer userId, Integer currentViews, String type,
////			String channel, String operateur, String pays, Integer adId, String device, String deviceName) throws Exception {
////		// MDC.clear();
////		slf4jLogger.info(
////				" ------------------------  l'execution de la methode asynchrone    ----------------------------");
////		PbViewNDto pbwDto = new PbViewNDto();
////		pbwDto.setUser_id(userId);
////		pbwDto.setCurrentViews(currentViews);
////		pbwDto.setType(type);
////		pbwDto.setChannel(channel);
////		pbwDto.setOperateur(operateur);
////		pbwDto.setPays(pays);
////		pbwDto.setAd_id(adId);
////		pbwDto.setDevice(device);
////		pbwDto.setDevice_name(deviceName);
//////		pbwDto.setDate_id(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
////		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
////		sf.format(new Date());
////		pbwDto.setDate_id(sf.format(new Date()));
////
////		trptClientEsUtils.insert(esConfig.getPbViewsIndexName()+ new SimpleDateFormat("ddMMyyyy").format(new Date()), "_doc",
////				"/templates/es/template_mynina_views_numbers.json", highClientFactory, 1024, pbwDto, RequestIdentifier);
////	}
//
//	@Async
//	public void createEsArticle(String RequestIdentifier, Integer id, Integer themeAssocieId,
//			String themeAssocieLibelle, String titre, String description, String libelle, Boolean isPublish,
//			Boolean isArchive, Integer producteurId, String ProducteurNamne, String motCles, String zoneGeographisue ) {
//
//		ArticleEsDto articleEsDto = new ArticleEsDto();
//		articleEsDto.setArticle_id(id);
//		articleEsDto.setTheme_associe_id(themeAssocieId);
//		articleEsDto.setTheme_associe_libelle(themeAssocieLibelle);
//		articleEsDto.setTitre(titre);
//		articleEsDto.setDescription(description);
//		articleEsDto.setLibelle(libelle);
//		articleEsDto.setIs_published(isPublish);
//		articleEsDto.setIs_archiveed(isArchive);
//		articleEsDto.setProducteur_id(producteurId);
//		articleEsDto.setProducteur_name(ProducteurNamne);
//		articleEsDto.setMot_cles(motCles);
//		articleEsDto.setZone_geographique(zoneGeographisue);
//		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
//		sf.format(new Date());
//		articleEsDto.setCreated_at(sf.format(new Date()));
//
//		try {
//			trptClientEsUtils.insert(
//					esConfig.getArticleCreateIndexName() + new SimpleDateFormat("ddMMyyyy").format(new Date()), "_doc",
//					"/templates/es/template_mynina_views_numbers.json", highClientFactory, 1024, articleEsDto,
//					RequestIdentifier);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public <T> Response<T> sendEmail(Map<String, String> from, List<Map<String, String>> toRecipients, String subject,
			String body, List<String> attachmentsFilesAbsolutePaths, Context context, String templateName,
			Locale locale) {
		Response<T> response = new Response<T>();
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		// /*
		// * A retirer
		// */
		// if(true)
		// return response;
		//
		// /*
		// * retirer
		// */

		String smtpServer = paramsUtils.getSmtpHost();
		if (smtpServer != null) {
			Boolean auth = false;
			javaMailSender.setHost(smtpServer);
			javaMailSender.setPort(paramsUtils.getSmtpPort());
			javaMailSender.setUsername(paramsUtils.getSmtpMailAdresse());
			javaMailSender.setPassword(paramsUtils.getSmtpPassword());

			// ADD NEW CONFIG for "no object DCH for MIME type multipart/mixed" error
			MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
			auth = true;

			javaMailSender.setJavaMailProperties(getMailProperties(paramsUtils.getSmtpHost(), auth));

			MimeMessage message = javaMailSender.createMimeMessage();
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message, Boolean.TRUE, "UTF-8");

				// sender
				helper.setFrom(new InternetAddress(from.get("email"), from.get("user")));
				// sender

				// recipients
				List<InternetAddress> to = new ArrayList<InternetAddress>();
				for (Map<String, String> recipient : toRecipients) {
					to.add(new InternetAddress(recipient.get("email"), recipient.get("user")));
				}
				helper.setTo(to.toArray(new InternetAddress[0]));

				// Subject and body
				helper.setSubject(subject);
				if (context != null && templateName != null) {
					body = templateEngine.process(templateName, context);
				}
				helper.setText(body, true);

				// Attachments
				if (attachmentsFilesAbsolutePaths != null && !attachmentsFilesAbsolutePaths.isEmpty()) {
					for (String attachmentPath : attachmentsFilesAbsolutePaths) {
						File pieceJointe = new File(attachmentPath);
						FileSystemResource file = new FileSystemResource(attachmentPath);
						if (pieceJointe.exists() && pieceJointe.isFile()) {
							helper.addAttachment(file.getFilename(), file);
						}
					}
				}

				javaMailSender.send(message);
				response.setHasError(Boolean.FALSE);
				/// gerer les cas d'exeption de non envoi de mail
			} catch (MessagingException e) {
				e.printStackTrace();
				exceptionUtils.EXCEPTION(response, locale, e);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				exceptionUtils.EXCEPTION(response, locale, e);
			}
		}
		return response;
	}
	
	private Properties getMailProperties(String host, Boolean auth) {
		  Properties properties = new Properties();
		  properties.setProperty("mail.transport.protocol", "smtp");
		  properties.setProperty("mail.smtp.auth", auth.toString());
		  properties.setProperty("mail.smtp.starttls.enable", "true");
		  properties.setProperty("mail.smtp.starttls.required", "true");
		  properties.setProperty("mail.debug", "true");
		  if (host.equals("smtp.gmail.com")) {
		   properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
		  }
		  return properties;
		 }

}
