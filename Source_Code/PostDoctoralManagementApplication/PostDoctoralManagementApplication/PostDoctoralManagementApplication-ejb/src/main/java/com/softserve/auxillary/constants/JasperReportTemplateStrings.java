/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.auxillary.constants;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class JasperReportTemplateStrings {
    /**
     * Jasper report template for persons
     */
    public static final String PERSON = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"Person\" language=\"java\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\"535\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\" uuid=\"4947cb73-b79a-4a9c-896d-9c36325f125c\">\n" +
"	<queryString language=\"SQL\">\n" +
"		<![CDATA[SELECT\n" +
"     person.`_systemID` AS systemID,\n" +
"     person.`_title` AS title,\n" +
"     person.`_fullName` AS fullName,\n" +
"     person.`_surname` AS surname,\n" +
"     person.`_email` AS email,\n" +
"     person.`_telephoneNumber` AS telephoneNumber,\n" +
"     person.`_workNumber` AS workNumber,\n" +
"     person.`_faxNumber` AS faxNumber,\n" +
"     person.`_cellphoneNumber` AS cellphoneNumber\n" +
"FROM\n" +
"     `person` person]]>\n" +
"	</queryString>\n" +
"	<field name=\"systemID\" class=\"java.lang.String\"/>\n" +
"	<field name=\"title\" class=\"java.lang.String\"/>\n" +
"	<field name=\"fullName\" class=\"java.lang.String\"/>\n" +
"	<field name=\"surname\" class=\"java.lang.String\"/>\n" +
"	<field name=\"email\" class=\"java.lang.String\"/>\n" +
"	<field name=\"telephoneNumber\" class=\"java.lang.String\"/>\n" +
"	<field name=\"workNumber\" class=\"java.lang.String\"/>\n" +
"	<field name=\"faxNumber\" class=\"java.lang.String\"/>\n" +
"	<field name=\"cellphoneNumber\" class=\"java.lang.String\"/>\n" +
"	<group name=\"systemID\">\n" +
"		<groupExpression><![CDATA[$F{systemID}]]></groupExpression>\n" +
"		<groupHeader>\n" +
"			<band height=\"33\">\n" +
"				<staticText>\n" +
"					<reportElement mode=\"Opaque\" x=\"0\" y=\"0\" width=\"100\" height=\"32\" forecolor=\"#666666\" backcolor=\"#E6E6E6\" uuid=\"832a0c2b-8b5b-437d-a25f-671575476f19\"/>\n" +
"					<textElement>\n" +
"						<font size=\"12\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[systemID]]></text>\n" +
"				</staticText>\n" +
"				<textField>\n" +
"					<reportElement mode=\"Opaque\" x=\"100\" y=\"0\" width=\"455\" height=\"32\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"2e19d69e-45e6-492d-87f9-5ba2e070a3f5\"/>\n" +
"					<textElement>\n" +
"						<font size=\"24\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<textFieldExpression><![CDATA[$F{systemID}]]></textFieldExpression>\n" +
"				</textField>\n" +
"				<line>\n" +
"					<reportElement x=\"-20\" y=\"32\" width=\"595\" height=\"1\" forecolor=\"#666666\" uuid=\"663505e6-8eee-4f1a-b565-33596d556138\"/>\n" +
"				</line>\n" +
"			</band>\n" +
"		</groupHeader>\n" +
"		<groupFooter>\n" +
"			<band/>\n" +
"		</groupFooter>\n" +
"	</group>\n" +
"	<background>\n" +
"		<band/>\n" +
"	</background>\n" +
"	<title>\n" +
"		<band height=\"72\">\n" +
"			<frame>\n" +
"				<reportElement mode=\"Opaque\" x=\"-20\" y=\"-20\" width=\"595\" height=\"92\" backcolor=\"#006699\" uuid=\"fe92a48b-cca7-41a4-ab9a-546e90010aa4\"/>\n" +
"				<staticText>\n" +
"					<reportElement x=\"20\" y=\"20\" width=\"234\" height=\"43\" forecolor=\"#FFFFFF\" uuid=\"553cf3a9-6475-459b-a88c-bf96b1c01a39\"/>\n" +
"					<textElement>\n" +
"						<font size=\"34\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[TITLE]]></text>\n" +
"				</staticText>\n" +
"				<staticText>\n" +
"					<reportElement x=\"395\" y=\"43\" width=\"180\" height=\"20\" forecolor=\"#FFFFFF\" uuid=\"21d34bd4-58b9-4ca4-99ab-8be12b22daf5\"/>\n" +
"					<textElement textAlignment=\"Right\">\n" +
"						<font size=\"14\" isBold=\"false\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[Add a description here]]></text>\n" +
"				</staticText>\n" +
"			</frame>\n" +
"		</band>\n" +
"	</title>\n" +
"	<pageHeader>\n" +
"		<band height=\"13\"/>\n" +
"	</pageHeader>\n" +
"	<columnHeader>\n" +
"		<band height=\"21\">\n" +
"			<line>\n" +
"				<reportElement x=\"-20\" y=\"20\" width=\"595\" height=\"1\" forecolor=\"#666666\" uuid=\"d37106b2-d406-40f9-9d8f-25988e7d3370\"/>\n" +
"			</line>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"0\" y=\"0\" width=\"69\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"c59d8485-35cc-401f-832c-8e53e4307684\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[title]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"69\" y=\"0\" width=\"69\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"3a8e6aa3-5db8-48ab-956c-9d9b6d9f3567\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[fullName]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"138\" y=\"0\" width=\"69\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"8316f6ae-5e0a-4a48-8e6a-107d0c5cf2f0\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[surname]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"207\" y=\"0\" width=\"69\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"f6fa7ff9-9bc3-4f46-b83f-2d2c078ade61\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[email]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"276\" y=\"0\" width=\"69\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"e51d2de4-8c4a-4859-9b93-ac7b4cfb4d50\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[telephoneNumber]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"345\" y=\"0\" width=\"69\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"d7959332-7857-4cd4-acb0-8027e47294ea\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[workNumber]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"414\" y=\"0\" width=\"69\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"784ce25e-4dc9-4ae1-ade7-2386bb0d5b7c\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[faxNumber]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"483\" y=\"0\" width=\"69\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"49a0d18a-4fea-423a-ab56-ee7f390537fa\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[cellphoneNumber]]></text>\n" +
"			</staticText>\n" +
"		</band>\n" +
"	</columnHeader>\n" +
"	<detail>\n" +
"		<band height=\"20\">\n" +
"			<line>\n" +
"				<reportElement positionType=\"FixRelativeToBottom\" x=\"0\" y=\"19\" width=\"555\" height=\"1\" uuid=\"139070b8-310b-4a1c-9abc-20f04713a116\"/>\n" +
"			</line>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"0\" y=\"0\" width=\"69\" height=\"20\" uuid=\"21b64ba1-c5da-4664-8b60-e12b9e052fbc\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{title}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"69\" y=\"0\" width=\"69\" height=\"20\" uuid=\"d8505857-36c2-482f-a90d-1ac707e15168\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{fullName}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"138\" y=\"0\" width=\"69\" height=\"20\" uuid=\"79190f25-93af-4dab-9b2c-d72b26e5bb0b\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{surname}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"207\" y=\"0\" width=\"69\" height=\"20\" uuid=\"fb603695-7555-4af8-b8a6-6d8dcc1c3837\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{email}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"276\" y=\"0\" width=\"69\" height=\"20\" uuid=\"2ef08bb8-e031-451f-ae4a-d4037113767e\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{telephoneNumber}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"345\" y=\"0\" width=\"69\" height=\"20\" uuid=\"0798473b-08e6-474e-91d2-bd506ba104ce\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{workNumber}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"414\" y=\"0\" width=\"69\" height=\"20\" uuid=\"fab46ddc-287e-4087-9d36-7ff414fbfc9b\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{faxNumber}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"483\" y=\"0\" width=\"69\" height=\"20\" uuid=\"dc7059d4-8b0d-4f57-ba8e-75fdeb0db571\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{cellphoneNumber}]]></textFieldExpression>\n" +
"			</textField>\n" +
"		</band>\n" +
"	</detail>\n" +
"	<columnFooter>\n" +
"		<band/>\n" +
"	</columnFooter>\n" +
"	<pageFooter>\n" +
"		<band height=\"17\">\n" +
"			<textField>\n" +
"				<reportElement mode=\"Opaque\" x=\"0\" y=\"4\" width=\"515\" height=\"13\" backcolor=\"#E6E6E6\" uuid=\"15f740e2-3316-4d10-8558-b127d1cb9189\"/>\n" +
"				<textElement textAlignment=\"Right\"/>\n" +
"				<textFieldExpression><![CDATA[\"Page \"+$V{PAGE_NUMBER}+\" of\"]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField evaluationTime=\"Report\">\n" +
"				<reportElement mode=\"Opaque\" x=\"515\" y=\"4\" width=\"40\" height=\"13\" backcolor=\"#E6E6E6\" uuid=\"31a8c932-7b8a-4e26-b55a-0c150be4c4ab\"/>\n" +
"				<textFieldExpression><![CDATA[\" \" + $V{PAGE_NUMBER}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField pattern=\"EEEEE dd MMMMM yyyy\">\n" +
"				<reportElement x=\"0\" y=\"4\" width=\"100\" height=\"13\" uuid=\"be94f1ea-1e31-4dd7-a833-3aac63f8e7ba\"/>\n" +
"				<textFieldExpression><![CDATA[new java.util.Date()]]></textFieldExpression>\n" +
"			</textField>\n" +
"		</band>\n" +
"	</pageFooter>\n" +
"	<summary>\n" +
"		<band/>\n" +
"	</summary>\n" +
"</jasperReport>";
    /**
     * Jasper report template for all persons
     */
    public static final String ALL_PERSONS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"AllPersons\" language=\"java\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\"535\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\" uuid=\"f4a719fa-cbdb-4982-a800-e74a6ab58026\">\n" +
"	<queryString language=\"SQL\">\n" +
"		<![CDATA[SELECT\n" +
"     person.`_systemID` AS person__systemID,\n" +
"     person.`_title` AS person__title,\n" +
"     person.`_fullName` AS person__fullName,\n" +
"     person.`_surname` AS person__surname,\n" +
"     person.`_email` AS person__email,\n" +
"     person.`_telephoneNumber` AS person__telephoneNumber,\n" +
"     person.`_workNumber` AS person__workNumber,\n" +
"     person.`_faxNumber` AS person__faxNumber,\n" +
"     person.`_cellphoneNumber` AS person__cellphoneNumber,\n" +
"     security_role.`_name` AS security_role__name,\n" +
"     academic_qualification.`_name` AS academic_qualification__name,\n" +
"     cv.`_gender` AS cv__gender,\n" +
"     cv.`_citizenship` AS cv__citizenship,\n" +
"     address.`_country` AS address__country,\n" +
"     address.`_province` AS address__province,\n" +
"     address.`_town_city` AS address__town_city,\n" +
"     academic_qualification.`_fieldOfStudy` AS academic_qualification__fieldOfStudy\n" +
"FROM\n" +
"     `person` person INNER JOIN `person_security_role` person_security_role ON person.`_systemID` = person_security_role.`_personID`\n" +
"     INNER JOIN `security_role` security_role ON person_security_role.`_roleID` = security_role.`_roleID`\n" +
"     INNER JOIN `cv` cv ON person.`_systemID` = cv.`_cvID`\n" +
"     INNER JOIN `address` address ON person.`_addressLine1` = address.`_addressID`\n" +
"     INNER JOIN `academic_qualification` academic_qualification ON cv.`_cvID` = academic_qualification.`_cv`]]>\n" +
"	</queryString>\n" +
"	<field name=\"person__systemID\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__title\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__fullName\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__surname\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__email\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__telephoneNumber\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__workNumber\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__faxNumber\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__cellphoneNumber\" class=\"java.lang.String\"/>\n" +
"	<field name=\"security_role__name\" class=\"java.lang.String\"/>\n" +
"	<field name=\"academic_qualification__name\" class=\"java.lang.String\"/>\n" +
"	<field name=\"cv__gender\" class=\"java.lang.String\"/>\n" +
"	<field name=\"cv__citizenship\" class=\"java.lang.String\"/>\n" +
"	<field name=\"address__country\" class=\"java.lang.String\"/>\n" +
"	<field name=\"address__province\" class=\"java.lang.String\"/>\n" +
"	<field name=\"address__town_city\" class=\"java.lang.String\"/>\n" +
"	<field name=\"academic_qualification__fieldOfStudy\" class=\"java.lang.String\"/>\n" +
"	<group name=\"person__systemID\">\n" +
"		<groupExpression><![CDATA[$F{person__systemID}]]></groupExpression>\n" +
"		<groupHeader>\n" +
"			<band height=\"33\">\n" +
"				<staticText>\n" +
"					<reportElement mode=\"Opaque\" x=\"0\" y=\"0\" width=\"100\" height=\"32\" forecolor=\"#666666\" backcolor=\"#E6E6E6\" uuid=\"473a56b5-284c-428c-a590-88f1a4bced7d\"/>\n" +
"					<textElement>\n" +
"						<font size=\"12\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[person__systemID]]></text>\n" +
"				</staticText>\n" +
"				<textField>\n" +
"					<reportElement mode=\"Opaque\" x=\"100\" y=\"0\" width=\"455\" height=\"32\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"81820973-1122-4246-b6bd-38c037cd5e25\"/>\n" +
"					<textElement>\n" +
"						<font size=\"24\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<textFieldExpression><![CDATA[$F{person__systemID}]]></textFieldExpression>\n" +
"				</textField>\n" +
"				<line>\n" +
"					<reportElement x=\"-20\" y=\"32\" width=\"595\" height=\"1\" forecolor=\"#666666\" uuid=\"84606245-a30b-490f-8f38-58caf4d5e36e\"/>\n" +
"				</line>\n" +
"			</band>\n" +
"		</groupHeader>\n" +
"		<groupFooter>\n" +
"			<band/>\n" +
"		</groupFooter>\n" +
"	</group>\n" +
"	<group name=\"security_role__name\">\n" +
"		<groupExpression><![CDATA[$F{security_role__name}]]></groupExpression>\n" +
"		<groupHeader>\n" +
"			<band height=\"32\">\n" +
"				<textField>\n" +
"					<reportElement x=\"100\" y=\"0\" width=\"455\" height=\"32\" forecolor=\"#006699\" uuid=\"e0a5dd27-a3ae-42e4-a7ae-2fa6426a2f5f\"/>\n" +
"					<textElement>\n" +
"						<font size=\"22\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<textFieldExpression><![CDATA[$F{security_role__name}]]></textFieldExpression>\n" +
"				</textField>\n" +
"				<staticText>\n" +
"					<reportElement x=\"0\" y=\"0\" width=\"100\" height=\"20\" forecolor=\"#666666\" uuid=\"725d73af-fd41-4583-91d5-5b3b7f1da6b9\"/>\n" +
"					<textElement>\n" +
"						<font size=\"12\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[security_role__name]]></text>\n" +
"				</staticText>\n" +
"			</band>\n" +
"		</groupHeader>\n" +
"		<groupFooter>\n" +
"			<band/>\n" +
"		</groupFooter>\n" +
"	</group>\n" +
"	<group name=\"academic_qualification__fieldOfStudy\">\n" +
"		<groupExpression><![CDATA[$F{academic_qualification__fieldOfStudy}]]></groupExpression>\n" +
"		<groupHeader>\n" +
"			<band height=\"32\">\n" +
"				<textField>\n" +
"					<reportElement x=\"100\" y=\"0\" width=\"455\" height=\"32\" forecolor=\"#006699\" uuid=\"35c648f9-08b7-4da6-a6ee-01351ab3599b\"/>\n" +
"					<textElement>\n" +
"						<font size=\"20\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<textFieldExpression><![CDATA[$F{academic_qualification__fieldOfStudy}]]></textFieldExpression>\n" +
"				</textField>\n" +
"				<staticText>\n" +
"					<reportElement x=\"0\" y=\"0\" width=\"100\" height=\"20\" forecolor=\"#666666\" uuid=\"8f1f4c65-e037-4237-82f4-2e94350fb8eb\"/>\n" +
"					<textElement>\n" +
"						<font size=\"12\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[academic_qualification__fieldOfStudy]]></text>\n" +
"				</staticText>\n" +
"			</band>\n" +
"		</groupHeader>\n" +
"		<groupFooter>\n" +
"			<band/>\n" +
"		</groupFooter>\n" +
"	</group>\n" +
"	<group name=\"address__country\">\n" +
"		<groupExpression><![CDATA[$F{address__country}]]></groupExpression>\n" +
"		<groupHeader>\n" +
"			<band height=\"32\">\n" +
"				<textField>\n" +
"					<reportElement x=\"100\" y=\"0\" width=\"455\" height=\"32\" forecolor=\"#006699\" uuid=\"3e219fad-2963-424d-8c87-820882152156\"/>\n" +
"					<textElement>\n" +
"						<font size=\"16\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<textFieldExpression><![CDATA[$F{address__country}]]></textFieldExpression>\n" +
"				</textField>\n" +
"				<staticText>\n" +
"					<reportElement x=\"0\" y=\"0\" width=\"100\" height=\"20\" forecolor=\"#666666\" uuid=\"a8d37477-dfdf-40ad-989d-3a43dc67a8ca\"/>\n" +
"					<textElement>\n" +
"						<font size=\"12\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[address__country]]></text>\n" +
"				</staticText>\n" +
"			</band>\n" +
"		</groupHeader>\n" +
"		<groupFooter>\n" +
"			<band/>\n" +
"		</groupFooter>\n" +
"	</group>\n" +
"	<background>\n" +
"		<band/>\n" +
"	</background>\n" +
"	<title>\n" +
"		<band height=\"72\">\n" +
"			<frame>\n" +
"				<reportElement mode=\"Opaque\" x=\"-20\" y=\"-20\" width=\"595\" height=\"92\" backcolor=\"#006699\" uuid=\"46294f7f-e822-4769-a21e-55614c06caf5\"/>\n" +
"				<staticText>\n" +
"					<reportElement x=\"20\" y=\"20\" width=\"234\" height=\"43\" forecolor=\"#FFFFFF\" uuid=\"c7050714-0eb1-48da-9589-47bee5da607e\"/>\n" +
"					<textElement>\n" +
"						<font size=\"34\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[TITLE]]></text>\n" +
"				</staticText>\n" +
"				<staticText>\n" +
"					<reportElement x=\"395\" y=\"43\" width=\"180\" height=\"20\" forecolor=\"#FFFFFF\" uuid=\"ffe820b3-5832-4677-af35-28f1488c3b2f\"/>\n" +
"					<textElement textAlignment=\"Right\">\n" +
"						<font size=\"14\" isBold=\"false\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[Add a description here]]></text>\n" +
"				</staticText>\n" +
"			</frame>\n" +
"		</band>\n" +
"	</title>\n" +
"	<pageHeader>\n" +
"		<band height=\"13\"/>\n" +
"	</pageHeader>\n" +
"	<columnHeader>\n" +
"		<band height=\"21\">\n" +
"			<line>\n" +
"				<reportElement x=\"-20\" y=\"20\" width=\"595\" height=\"1\" forecolor=\"#666666\" uuid=\"396076d4-aa2e-406f-b5cd-ba2b1b268fd4\"/>\n" +
"			</line>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"0\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"f2591d24-0f3e-46d0-b79c-09443c8772a6\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__title]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"42\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"ded3b00a-3b4a-4d3a-b030-4995a636a5ca\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__fullName]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"84\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"9cb8bbdc-14d7-44d2-8b7a-f73b80c34718\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__surname]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"126\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"4bea9d95-42ca-41bd-817e-1a54d224400e\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__email]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"168\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"34f480eb-3448-45c3-8164-a6adff552caa\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__telephoneNumber]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"210\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"c5c04c34-bc58-42ea-8052-575f06601fde\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__workNumber]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"252\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"0d03157b-29d2-44b5-a74b-bf7e456a62fd\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__faxNumber]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"294\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"f0f35e2f-d4d1-4254-a66c-68e12512c005\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__cellphoneNumber]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"336\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"81358b1c-986b-4f26-9df5-bd4f3cef492c\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[academic_qualification__name]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"378\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"e21a1b71-f9d5-4255-97b4-50284a6033f4\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[cv__gender]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"420\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"2df8b1b5-ea9f-4eb4-a086-35b0005bcac6\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[cv__citizenship]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"462\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"b4236b53-465f-4f32-92f8-0f8ec9476546\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[address__province]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"504\" y=\"0\" width=\"42\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"aea72c92-65f6-49aa-9603-02433a5a5a03\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[address__town_city]]></text>\n" +
"			</staticText>\n" +
"		</band>\n" +
"	</columnHeader>\n" +
"	<detail>\n" +
"		<band height=\"20\">\n" +
"			<line>\n" +
"				<reportElement positionType=\"FixRelativeToBottom\" x=\"0\" y=\"19\" width=\"555\" height=\"1\" uuid=\"e9b357a0-0745-49f7-ac1a-03ae73f9d01f\"/>\n" +
"			</line>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"0\" y=\"0\" width=\"42\" height=\"20\" uuid=\"db0394cb-2cf8-4eba-8a67-ba78b07bf73c\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__title}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"42\" y=\"0\" width=\"42\" height=\"20\" uuid=\"9f381292-25ea-418d-ac73-6dfc4446cef8\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__fullName}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"84\" y=\"0\" width=\"42\" height=\"20\" uuid=\"8cbaa48f-d26f-4b92-b4ea-accbaa66ba86\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__surname}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"126\" y=\"0\" width=\"42\" height=\"20\" uuid=\"9b46eeb0-ec42-4212-b749-6b6dc75de1ce\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__email}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"168\" y=\"0\" width=\"42\" height=\"20\" uuid=\"8d84440f-5bb2-428a-8f5a-28626b30a8df\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__telephoneNumber}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"210\" y=\"0\" width=\"42\" height=\"20\" uuid=\"d7428d53-2b45-4f7e-9cd9-275fcb911c86\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__workNumber}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"252\" y=\"0\" width=\"42\" height=\"20\" uuid=\"48114331-747c-48f0-92b8-c523ae7288e6\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__faxNumber}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"294\" y=\"0\" width=\"42\" height=\"20\" uuid=\"19c939b7-466b-4af8-8fb3-493f3c18fd71\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__cellphoneNumber}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"336\" y=\"0\" width=\"42\" height=\"20\" uuid=\"01c7e384-96c7-4afa-b33b-6926cde10c8f\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{academic_qualification__name}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"378\" y=\"0\" width=\"42\" height=\"20\" uuid=\"5ed49520-ab4f-40bb-b95f-f270f0b16364\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{cv__gender}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"420\" y=\"0\" width=\"42\" height=\"20\" uuid=\"de62b460-c10e-4475-8905-3ef0e04244c3\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{cv__citizenship}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"462\" y=\"0\" width=\"42\" height=\"20\" uuid=\"54619347-8d6b-442f-b7d1-4371c1af4acd\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{address__province}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"504\" y=\"0\" width=\"42\" height=\"20\" uuid=\"7628934c-874d-475b-89da-dc55f31be8e6\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{address__town_city}]]></textFieldExpression>\n" +
"			</textField>\n" +
"		</band>\n" +
"	</detail>\n" +
"	<columnFooter>\n" +
"		<band/>\n" +
"	</columnFooter>\n" +
"	<pageFooter>\n" +
"		<band height=\"17\">\n" +
"			<textField>\n" +
"				<reportElement mode=\"Opaque\" x=\"0\" y=\"4\" width=\"515\" height=\"13\" backcolor=\"#E6E6E6\" uuid=\"2acdc227-86b0-4b3c-ab28-99ef8e5fd0f7\"/>\n" +
"				<textElement textAlignment=\"Right\"/>\n" +
"				<textFieldExpression><![CDATA[\"Page \"+$V{PAGE_NUMBER}+\" of\"]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField evaluationTime=\"Report\">\n" +
"				<reportElement mode=\"Opaque\" x=\"515\" y=\"4\" width=\"40\" height=\"13\" backcolor=\"#E6E6E6\" uuid=\"6d6cf55a-7990-4fcb-b6be-e009c7c9cdb9\"/>\n" +
"				<textFieldExpression><![CDATA[\" \" + $V{PAGE_NUMBER}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField pattern=\"EEEEE dd MMMMM yyyy\">\n" +
"				<reportElement x=\"0\" y=\"4\" width=\"100\" height=\"13\" uuid=\"6a982063-2582-4c71-8d25-ae6167cb6d29\"/>\n" +
"				<textFieldExpression><![CDATA[new java.util.Date()]]></textFieldExpression>\n" +
"			</textField>\n" +
"		</band>\n" +
"	</pageFooter>\n" +
"	<summary>\n" +
"		<band/>\n" +
"	</summary>\n" +
"</jasperReport>";
    /**
     * Jasper report template for applications
     */
    public static final String APPLICATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"Application\" language=\"java\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\"535\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\" uuid=\"90e89b36-373a-4d23-9b1c-d02408649b81\">\n" +
"	<queryString language=\"SQL\">\n" +
"		<![CDATA[SELECT\n" +
"     application.`_applicationID` AS applicationID,\n" +
"     application.`_type` AS type,\n" +
"     application.`_status` AS status,\n" +
"     application.`_timestamp` AS timestamp,\n" +
"     application.`_submissionDate` AS submissionDate,\n" +
"     application.`_finalisationDate` AS finalisationDate,\n" +
"     application.`_startDate` AS startDate,\n" +
"     application.`_endDate` AS endDate,\n" +
"     application.`_projectTitle` AS projectTitle\n" +
"FROM\n" +
"     `application` application]]>\n" +
"	</queryString>\n" +
"	<field name=\"applicationID\" class=\"java.math.BigInteger\"/>\n" +
"	<field name=\"type\" class=\"java.lang.String\"/>\n" +
"	<field name=\"status\" class=\"java.lang.String\"/>\n" +
"	<field name=\"timestamp\" class=\"java.sql.Timestamp\"/>\n" +
"	<field name=\"submissionDate\" class=\"java.sql.Timestamp\"/>\n" +
"	<field name=\"finalisationDate\" class=\"java.sql.Timestamp\"/>\n" +
"	<field name=\"startDate\" class=\"java.sql.Date\"/>\n" +
"	<field name=\"endDate\" class=\"java.sql.Date\"/>\n" +
"	<field name=\"projectTitle\" class=\"java.lang.String\"/>\n" +
"	<background>\n" +
"		<band/>\n" +
"	</background>\n" +
"	<title>\n" +
"		<band height=\"72\">\n" +
"			<frame>\n" +
"				<reportElement mode=\"Opaque\" x=\"-20\" y=\"-20\" width=\"595\" height=\"92\" backcolor=\"#006699\" uuid=\"b880b28e-ccd2-4b0b-8883-78a2f907af06\"/>\n" +
"				<staticText>\n" +
"					<reportElement x=\"20\" y=\"20\" width=\"234\" height=\"43\" forecolor=\"#FFFFFF\" uuid=\"c395c96d-77e7-43bd-a545-871bd7abc37f\"/>\n" +
"					<textElement>\n" +
"						<font size=\"34\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[TITLE]]></text>\n" +
"				</staticText>\n" +
"				<staticText>\n" +
"					<reportElement x=\"395\" y=\"43\" width=\"180\" height=\"20\" forecolor=\"#FFFFFF\" uuid=\"44292d7e-c360-43fd-a20b-52a58bcc04dd\"/>\n" +
"					<textElement textAlignment=\"Right\">\n" +
"						<font size=\"14\" isBold=\"false\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[Add a description here]]></text>\n" +
"				</staticText>\n" +
"			</frame>\n" +
"		</band>\n" +
"	</title>\n" +
"	<pageHeader>\n" +
"		<band height=\"13\"/>\n" +
"	</pageHeader>\n" +
"	<columnHeader>\n" +
"		<band height=\"21\">\n" +
"			<line>\n" +
"				<reportElement x=\"-20\" y=\"20\" width=\"595\" height=\"1\" forecolor=\"#666666\" uuid=\"e15d5d45-3839-4e2e-8684-774713c29455\"/>\n" +
"			</line>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"0\" y=\"0\" width=\"61\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"ec1cda29-919a-4d29-91f3-d5ba230bf912\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[applicationID]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"61\" y=\"0\" width=\"61\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"aa5d36da-abba-48b3-9c85-81d5692b224f\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[type]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"122\" y=\"0\" width=\"61\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"c80ace18-206a-48d7-8d8b-c323721072c3\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[status]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"183\" y=\"0\" width=\"61\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"a75034ff-6866-4a01-b44e-4b072d3ffb16\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[timestamp]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"244\" y=\"0\" width=\"61\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"c97e8077-cab6-4f77-b45d-f5e93e4d013d\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[submissionDate]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"305\" y=\"0\" width=\"61\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"523cc182-a053-425a-adb2-06310e2324aa\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[finalisationDate]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"366\" y=\"0\" width=\"61\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"81040075-8d88-4320-9b54-67a6b43894bb\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[startDate]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"427\" y=\"0\" width=\"61\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"fa8e6fb9-7b5c-407c-be1a-76b3c1e62f22\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[endDate]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"488\" y=\"0\" width=\"61\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"4be33faf-0358-474e-b620-66ca7e87241a\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[projectTitle]]></text>\n" +
"			</staticText>\n" +
"		</band>\n" +
"	</columnHeader>\n" +
"	<detail>\n" +
"		<band height=\"20\">\n" +
"			<line>\n" +
"				<reportElement positionType=\"FixRelativeToBottom\" x=\"0\" y=\"19\" width=\"555\" height=\"1\" uuid=\"302196c9-0abf-4a6a-a6b7-4def9f6915f5\"/>\n" +
"			</line>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"0\" y=\"0\" width=\"61\" height=\"20\" uuid=\"b7cb3828-6823-4882-9f9c-f596b6ea9b1e\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{applicationID}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"61\" y=\"0\" width=\"61\" height=\"20\" uuid=\"a9b4ab5f-9bed-4de1-a35a-eedf016688c0\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{type}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"122\" y=\"0\" width=\"61\" height=\"20\" uuid=\"140b8c9c-8c7a-4a73-a389-db1d68877d32\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{status}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"183\" y=\"0\" width=\"61\" height=\"20\" uuid=\"b511e2e6-55eb-47c3-903b-f9d065eaca6e\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{timestamp}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"244\" y=\"0\" width=\"61\" height=\"20\" uuid=\"dd35b91d-5be7-4d83-9387-546aea22c93c\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{submissionDate}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"305\" y=\"0\" width=\"61\" height=\"20\" uuid=\"dcd6797f-6bf0-449a-a384-3f637134422a\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{finalisationDate}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"366\" y=\"0\" width=\"61\" height=\"20\" uuid=\"68f55505-142c-4636-aa46-78c04438e423\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{startDate}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"427\" y=\"0\" width=\"61\" height=\"20\" uuid=\"2c331333-55cc-411d-ad30-5e6da753d49a\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{endDate}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"488\" y=\"0\" width=\"61\" height=\"20\" uuid=\"d9b19dc2-52f2-457d-9fa8-0e3716568aeb\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{projectTitle}]]></textFieldExpression>\n" +
"			</textField>\n" +
"		</band>\n" +
"	</detail>\n" +
"	<columnFooter>\n" +
"		<band/>\n" +
"	</columnFooter>\n" +
"	<pageFooter>\n" +
"		<band height=\"17\">\n" +
"			<textField>\n" +
"				<reportElement mode=\"Opaque\" x=\"0\" y=\"4\" width=\"515\" height=\"13\" backcolor=\"#E6E6E6\" uuid=\"b517e54b-ef7e-45b3-9b36-59e913392c05\"/>\n" +
"				<textElement textAlignment=\"Right\"/>\n" +
"				<textFieldExpression><![CDATA[\"Page \"+$V{PAGE_NUMBER}+\" of\"]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField evaluationTime=\"Report\">\n" +
"				<reportElement mode=\"Opaque\" x=\"515\" y=\"4\" width=\"40\" height=\"13\" backcolor=\"#E6E6E6\" uuid=\"ec7409a2-21af-45d5-a065-a99f64a95c52\"/>\n" +
"				<textFieldExpression><![CDATA[\" \" + $V{PAGE_NUMBER}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField pattern=\"EEEEE dd MMMMM yyyy\">\n" +
"				<reportElement x=\"0\" y=\"4\" width=\"100\" height=\"13\" uuid=\"9d4be204-7506-4790-b7fd-301f45b1a274\"/>\n" +
"				<textFieldExpression><![CDATA[new java.util.Date()]]></textFieldExpression>\n" +
"			</textField>\n" +
"		</band>\n" +
"	</pageFooter>\n" +
"	<summary>\n" +
"		<band/>\n" +
"	</summary>\n" +
"</jasperReport>";
    /**
     * Jasper report template for all applications
     */
    public static final String ALL_APPLICATIONS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"AllApplications\" language=\"java\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\"535\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\" uuid=\"6e0c965b-452c-405c-a4b6-81c9ac08dd2a\">\n" +
"	<queryString language=\"SQL\">\n" +
"		<![CDATA[SELECT\n" +
"     application.`_applicationID` AS application__applicationID,\n" +
"     application.`_type` AS application__type,\n" +
"     application.`_status` AS application__status,\n" +
"     application.`_timestamp` AS application__timestamp,\n" +
"     application.`_submissionDate` AS application__submissionDate,\n" +
"     application.`_finalisationDate` AS application__finalisationDate,\n" +
"     application.`_startDate` AS application__startDate,\n" +
"     application.`_endDate` AS application__endDate,\n" +
"     application.`_projectTitle` AS application__projectTitle,\n" +
"     application.`_information` AS application__information,\n" +
"     application.`_fellow` AS application__fellow,\n" +
"     application.`_grantHolder` AS application__grantHolder,\n" +
"     person.`_systemID` AS person__systemID,\n" +
"     person.`_title` AS person__title,\n" +
"     person.`_fullName` AS person__fullName,\n" +
"     person.`_surname` AS person__surname,\n" +
"     person.`_email` AS person__email,\n" +
"     security_role.`_name` AS security_role__name\n" +
"FROM\n" +
"     `person` person INNER JOIN `application` application ON person.`_systemID` = application.`_fellow`\n" +
"     AND person.`_systemID` = application.`_grantHolder`\n" +
"     INNER JOIN `person_security_role` person_security_role ON person.`_systemID` = person_security_role.`_personID`\n" +
"     INNER JOIN `security_role` security_role ON person_security_role.`_roleID` = security_role.`_roleID`]]>\n" +
"	</queryString>\n" +
"	<field name=\"application__applicationID\" class=\"java.math.BigInteger\"/>\n" +
"	<field name=\"application__type\" class=\"java.lang.String\"/>\n" +
"	<field name=\"application__status\" class=\"java.lang.String\"/>\n" +
"	<field name=\"application__timestamp\" class=\"java.sql.Timestamp\"/>\n" +
"	<field name=\"application__submissionDate\" class=\"java.sql.Timestamp\"/>\n" +
"	<field name=\"application__finalisationDate\" class=\"java.sql.Timestamp\"/>\n" +
"	<field name=\"application__startDate\" class=\"java.sql.Date\"/>\n" +
"	<field name=\"application__endDate\" class=\"java.sql.Date\"/>\n" +
"	<field name=\"application__projectTitle\" class=\"java.lang.String\"/>\n" +
"	<field name=\"application__information\" class=\"java.lang.String\"/>\n" +
"	<field name=\"application__fellow\" class=\"java.lang.String\"/>\n" +
"	<field name=\"application__grantHolder\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__systemID\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__title\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__fullName\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__surname\" class=\"java.lang.String\"/>\n" +
"	<field name=\"person__email\" class=\"java.lang.String\"/>\n" +
"	<field name=\"security_role__name\" class=\"java.lang.String\"/>\n" +
"	<group name=\"application__type\">\n" +
"		<groupExpression><![CDATA[$F{application__type}]]></groupExpression>\n" +
"		<groupHeader>\n" +
"			<band height=\"33\">\n" +
"				<staticText>\n" +
"					<reportElement mode=\"Opaque\" x=\"0\" y=\"0\" width=\"100\" height=\"32\" forecolor=\"#666666\" backcolor=\"#E6E6E6\" uuid=\"75f41337-903b-4914-be62-5716d4eea018\"/>\n" +
"					<textElement>\n" +
"						<font size=\"12\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[application__type]]></text>\n" +
"				</staticText>\n" +
"				<textField>\n" +
"					<reportElement mode=\"Opaque\" x=\"100\" y=\"0\" width=\"455\" height=\"32\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"56bb2679-309a-4c76-8146-d2be56c5f593\"/>\n" +
"					<textElement>\n" +
"						<font size=\"24\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<textFieldExpression><![CDATA[$F{application__type}]]></textFieldExpression>\n" +
"				</textField>\n" +
"				<line>\n" +
"					<reportElement x=\"-20\" y=\"32\" width=\"595\" height=\"1\" forecolor=\"#666666\" uuid=\"c2c86b65-64fe-4306-b2dc-47545730236c\"/>\n" +
"				</line>\n" +
"			</band>\n" +
"		</groupHeader>\n" +
"		<groupFooter>\n" +
"			<band/>\n" +
"		</groupFooter>\n" +
"	</group>\n" +
"	<group name=\"person__systemID\">\n" +
"		<groupExpression><![CDATA[$F{person__systemID}]]></groupExpression>\n" +
"		<groupHeader>\n" +
"			<band height=\"32\">\n" +
"				<textField>\n" +
"					<reportElement x=\"100\" y=\"0\" width=\"455\" height=\"32\" forecolor=\"#006699\" uuid=\"5759527a-3801-419f-8540-8ac983f1ba20\"/>\n" +
"					<textElement>\n" +
"						<font size=\"22\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<textFieldExpression><![CDATA[$F{person__systemID}]]></textFieldExpression>\n" +
"				</textField>\n" +
"				<staticText>\n" +
"					<reportElement x=\"0\" y=\"0\" width=\"100\" height=\"20\" forecolor=\"#666666\" uuid=\"0824c7c2-6d72-4e9d-b834-309265feee9c\"/>\n" +
"					<textElement>\n" +
"						<font size=\"12\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[person__systemID]]></text>\n" +
"				</staticText>\n" +
"			</band>\n" +
"		</groupHeader>\n" +
"		<groupFooter>\n" +
"			<band/>\n" +
"		</groupFooter>\n" +
"	</group>\n" +
"	<group name=\"security_role__name\">\n" +
"		<groupExpression><![CDATA[$F{security_role__name}]]></groupExpression>\n" +
"		<groupHeader>\n" +
"			<band height=\"32\">\n" +
"				<textField>\n" +
"					<reportElement x=\"100\" y=\"0\" width=\"455\" height=\"32\" forecolor=\"#006699\" uuid=\"e3fff0d2-81a6-4ae4-b131-b7879397f714\"/>\n" +
"					<textElement>\n" +
"						<font size=\"20\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<textFieldExpression><![CDATA[$F{security_role__name}]]></textFieldExpression>\n" +
"				</textField>\n" +
"				<staticText>\n" +
"					<reportElement x=\"0\" y=\"0\" width=\"100\" height=\"20\" forecolor=\"#666666\" uuid=\"7ced1e20-b536-4946-b4aa-9daf246076d4\"/>\n" +
"					<textElement>\n" +
"						<font size=\"12\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[security_role__name]]></text>\n" +
"				</staticText>\n" +
"			</band>\n" +
"		</groupHeader>\n" +
"		<groupFooter>\n" +
"			<band/>\n" +
"		</groupFooter>\n" +
"	</group>\n" +
"	<background>\n" +
"		<band/>\n" +
"	</background>\n" +
"	<title>\n" +
"		<band height=\"72\">\n" +
"			<frame>\n" +
"				<reportElement mode=\"Opaque\" x=\"-20\" y=\"-20\" width=\"595\" height=\"92\" backcolor=\"#006699\" uuid=\"8ef84198-1e58-4fb5-8e60-7dc0860bc694\"/>\n" +
"				<staticText>\n" +
"					<reportElement x=\"20\" y=\"20\" width=\"234\" height=\"43\" forecolor=\"#FFFFFF\" uuid=\"8cfd62e4-aa4f-4427-87df-b585d5fd259d\"/>\n" +
"					<textElement>\n" +
"						<font size=\"34\" isBold=\"true\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[TITLE]]></text>\n" +
"				</staticText>\n" +
"				<staticText>\n" +
"					<reportElement x=\"395\" y=\"43\" width=\"180\" height=\"20\" forecolor=\"#FFFFFF\" uuid=\"c40cff9b-5f91-45f8-a55c-4044140133b9\"/>\n" +
"					<textElement textAlignment=\"Right\">\n" +
"						<font size=\"14\" isBold=\"false\"/>\n" +
"					</textElement>\n" +
"					<text><![CDATA[Add a description here]]></text>\n" +
"				</staticText>\n" +
"			</frame>\n" +
"		</band>\n" +
"	</title>\n" +
"	<pageHeader>\n" +
"		<band height=\"13\"/>\n" +
"	</pageHeader>\n" +
"	<columnHeader>\n" +
"		<band height=\"21\">\n" +
"			<line>\n" +
"				<reportElement x=\"-20\" y=\"20\" width=\"595\" height=\"1\" forecolor=\"#666666\" uuid=\"e40ce656-ef17-4ae6-a63e-cc6a9fc053b5\"/>\n" +
"			</line>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"0\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"faeba2d0-7d68-4e95-9498-ec79926934bf\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[application__applicationID]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"37\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"7a544ec5-d397-4352-9623-482ad0b744ed\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[application__status]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"74\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"17a00b69-09f7-4aa5-b504-2b0640c18761\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[application__timestamp]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"111\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"1cf215f2-657f-4340-b316-65c5fa92f82b\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[application__submissionDate]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"148\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"823869f1-27f2-44e1-a5b7-9fc8eefa5886\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[application__finalisationDate]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"185\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"462e2a25-3912-4138-b238-2498e5cb00d1\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[application__startDate]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"222\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"589171fa-9832-48fc-9c4c-8db52eac8745\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[application__endDate]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"259\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"d195a0b9-364d-4f77-9646-06bcdc138e32\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[application__projectTitle]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"296\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"8e993d70-9c3e-46bc-803e-b8db6325d050\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[application__information]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"333\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"8ad3ef10-0064-4a60-84a4-cdcfaea5ca5a\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[application__fellow]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"370\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"18dde1bc-78dc-498f-96a4-5cf45f8652f7\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[application__grantHolder]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"407\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"efdb11c1-177d-412c-816c-efa8101cf1d4\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__title]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"444\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"e6e1aa0c-2587-4856-8200-86363cdb22ca\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__fullName]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"481\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"dd77bda4-bf7d-4684-93f9-cab160317233\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__surname]]></text>\n" +
"			</staticText>\n" +
"			<staticText>\n" +
"				<reportElement mode=\"Opaque\" x=\"518\" y=\"0\" width=\"37\" height=\"20\" forecolor=\"#006699\" backcolor=\"#E6E6E6\" uuid=\"f8a2ec58-1dc9-4a53-8d89-4c3d6722f02a\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"14\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<text><![CDATA[person__email]]></text>\n" +
"			</staticText>\n" +
"		</band>\n" +
"	</columnHeader>\n" +
"	<detail>\n" +
"		<band height=\"20\">\n" +
"			<line>\n" +
"				<reportElement positionType=\"FixRelativeToBottom\" x=\"0\" y=\"19\" width=\"555\" height=\"1\" uuid=\"044feb3c-f492-4db6-ab88-ae498e142dd5\"/>\n" +
"			</line>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"0\" y=\"0\" width=\"37\" height=\"20\" uuid=\"37054150-4489-4f53-b855-f85f20e18317\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{application__applicationID}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"37\" y=\"0\" width=\"37\" height=\"20\" uuid=\"46a7518b-1333-45b1-9b0b-946bea32864c\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{application__status}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"74\" y=\"0\" width=\"37\" height=\"20\" uuid=\"a794e466-04d8-4cf5-b47d-58ace53e4061\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{application__timestamp}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"111\" y=\"0\" width=\"37\" height=\"20\" uuid=\"a9547b60-8072-48f9-923b-9b56a3ab6a8f\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{application__submissionDate}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"148\" y=\"0\" width=\"37\" height=\"20\" uuid=\"071fb330-9e71-4c15-9e16-dd3c2522aff6\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{application__finalisationDate}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"185\" y=\"0\" width=\"37\" height=\"20\" uuid=\"3537d215-16fa-4870-a08d-c3022e7ba0aa\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{application__startDate}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"222\" y=\"0\" width=\"37\" height=\"20\" uuid=\"c48d8902-f9d0-44e9-8be2-8b3fcae305d1\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{application__endDate}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"259\" y=\"0\" width=\"37\" height=\"20\" uuid=\"83089c4e-1f32-447e-8b45-e2a6595a3d66\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{application__projectTitle}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"296\" y=\"0\" width=\"37\" height=\"20\" uuid=\"4991945f-4850-43c0-82de-4783d56d779e\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{application__information}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"333\" y=\"0\" width=\"37\" height=\"20\" uuid=\"4cef606e-8560-4a21-ab20-06d1b74ed2c4\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{application__fellow}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"370\" y=\"0\" width=\"37\" height=\"20\" uuid=\"4b9d0ae8-1924-442e-8cb4-1dc409589b23\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{application__grantHolder}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"407\" y=\"0\" width=\"37\" height=\"20\" uuid=\"62c65711-cdcc-4981-a425-2cc43156fc61\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__title}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"444\" y=\"0\" width=\"37\" height=\"20\" uuid=\"5f5a4795-bd2c-4723-aa05-5850755aa050\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__fullName}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"481\" y=\"0\" width=\"37\" height=\"20\" uuid=\"0fbaa8c1-94a3-441f-a082-b868a9ca4d0a\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__surname}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField isStretchWithOverflow=\"true\">\n" +
"				<reportElement x=\"518\" y=\"0\" width=\"37\" height=\"20\" uuid=\"b124be68-b265-4ef2-ad84-d09f131d55fb\"/>\n" +
"				<textElement>\n" +
"					<font size=\"14\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$F{person__email}]]></textFieldExpression>\n" +
"			</textField>\n" +
"		</band>\n" +
"	</detail>\n" +
"	<columnFooter>\n" +
"		<band/>\n" +
"	</columnFooter>\n" +
"	<pageFooter>\n" +
"		<band height=\"17\">\n" +
"			<textField>\n" +
"				<reportElement mode=\"Opaque\" x=\"0\" y=\"4\" width=\"515\" height=\"13\" backcolor=\"#E6E6E6\" uuid=\"eda45f2f-4567-4bbb-8202-37469ad85960\"/>\n" +
"				<textElement textAlignment=\"Right\"/>\n" +
"				<textFieldExpression><![CDATA[\"Page \"+$V{PAGE_NUMBER}+\" of\"]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField evaluationTime=\"Report\">\n" +
"				<reportElement mode=\"Opaque\" x=\"515\" y=\"4\" width=\"40\" height=\"13\" backcolor=\"#E6E6E6\" uuid=\"0b6209c9-9d10-4825-b17a-ff14a60f87b6\"/>\n" +
"				<textFieldExpression><![CDATA[\" \" + $V{PAGE_NUMBER}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField pattern=\"EEEEE dd MMMMM yyyy\">\n" +
"				<reportElement x=\"0\" y=\"4\" width=\"100\" height=\"13\" uuid=\"f4e7026e-30b0-49d2-9b18-ed2e9a958f07\"/>\n" +
"				<textFieldExpression><![CDATA[new java.util.Date()]]></textFieldExpression>\n" +
"			</textField>\n" +
"		</band>\n" +
"	</pageFooter>\n" +
"	<summary>\n" +
"		<band/>\n" +
"	</summary>\n" +
"</jasperReport>";
    public static final String DYNAMIC_PERSON = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"SiemPdfPrototype\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\"555\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\" uuid=\"f11e581d-1fe5-4a84-a06d-10f7c2322f73\">\n" +
"	<property name=\"ireport.zoom\" value=\"1.5\"/>\n" +
"	<property name=\"ireport.x\" value=\"0\"/>\n" +
"	<property name=\"ireport.y\" value=\"0\"/>\n" +
"	<parameter name=\"REPORT_TITLE\" class=\"java.lang.String\"/>\n" +
"	<parameter name=\"SQL\" class=\"java.lang.String\"/>\n" +
"	<parameter name=\"HEADER_IMAGE\" class=\"java.lang.String\"/>\n" +
"	<parameter name=\"CHART_IMAGE\" class=\"java.lang.String\"/>\n" +
"	<background>\n" +
"		<band splitType=\"Stretch\"/>\n" +
"	</background>\n" +
"	<title>\n" +
"		<band height=\"85\" splitType=\"Stretch\">\n" +
"			<textField>\n" +
"				<reportElement x=\"123\" y=\"43\" width=\"303\" height=\"32\" uuid=\"7ed703bc-8c60-478f-babd-c3e1cd45b9fb\"/>\n" +
"				<textElement textAlignment=\"Center\">\n" +
"					<font size=\"22\" isBold=\"true\"/>\n" +
"				</textElement>\n" +
"				<textFieldExpression><![CDATA[$P{REPORT_TITLE}]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<staticText>\n" +
"				<reportElement x=\"-286\" y=\"16\" width=\"100\" height=\"20\" uuid=\"f937fcf1-d9b4-4d99-865c-e3f77772acb8\"/>\n" +
"				<text><![CDATA[run on ]]></text>\n" +
"			</staticText>\n" +
"			<textField pattern=\"dd MMMMM yyyy\">\n" +
"				<reportElement x=\"426\" y=\"0\" width=\"129\" height=\"20\" uuid=\"39ece12f-0d1a-4809-8f45-107c1934c927\"/>\n" +
"				<textFieldExpression><![CDATA[new java.util.Date()]]></textFieldExpression>\n" +
"			</textField>\n" +
"		</band>\n" +
"	</title>\n" +
"	<pageHeader>\n" +
"		<band height=\"8\" splitType=\"Stretch\"/>\n" +
"	</pageHeader>\n" +
"	<columnHeader>\n" +
"		<band height=\"20\" splitType=\"Stretch\"/>\n" +
"	</columnHeader>\n" +
"	<columnFooter>\n" +
"		<band splitType=\"Stretch\"/>\n" +
"	</columnFooter>\n" +
"	<pageFooter>\n" +
"		<band height=\"31\" splitType=\"Stretch\">\n" +
"			<textField>\n" +
"				<reportElement x=\"222\" y=\"11\" width=\"80\" height=\"20\" uuid=\"8c0e1205-87c0-43cc-823b-d4af96852884\"/>\n" +
"				<textElement textAlignment=\"Right\"/>\n" +
"				<textFieldExpression><![CDATA[\"Page \"+$V{PAGE_NUMBER}+\" of\"]]></textFieldExpression>\n" +
"			</textField>\n" +
"			<textField evaluationTime=\"Report\">\n" +
"				<reportElement x=\"302\" y=\"11\" width=\"40\" height=\"20\" uuid=\"fee10489-4118-47d5-8908-f333367d52a4\"/>\n" +
"				<textFieldExpression><![CDATA[\" \" + $V{PAGE_NUMBER}]]></textFieldExpression>\n" +
"			</textField>\n" +
"		</band>\n" +
"	</pageFooter>\n" +
"	<summary>\n" +
"		<band splitType=\"Stretch\"/>\n" +
"	</summary>\n" +
"</jasperReport>"; 
}
