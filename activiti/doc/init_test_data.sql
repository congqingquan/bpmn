-- 流程部署表
INSERT INTO `activiti`.`act_re_deployment` ( `ID_`, `NAME_`, `CATEGORY_`, `KEY_`, `TENANT_ID_`, `DEPLOY_TIME_`, `ENGINE_VERSION_`, `VERSION_`, `PROJECT_RELEASE_VERSION_` )
VALUES
    ( '8f6216eb-dada-11ee-a27d-20c19b7a45c3', '提现审核流程', NULL, NULL, '', '2024-03-05 18:24:33.836', NULL, 1, NULL );

-- 流程定义表
INSERT INTO `activiti`.`act_re_procdef` (
    `ID_`,
    `REV_`,
    `CATEGORY_`,
    `NAME_`,
    `KEY_`,
    `VERSION_`,
    `DEPLOYMENT_ID_`,
    `RESOURCE_NAME_`,
    `DGRM_RESOURCE_NAME_`,
    `DESCRIPTION_`,
    `HAS_START_FORM_KEY_`,
    `HAS_GRAPHICAL_NOTATION_`,
    `SUSPENSION_STATE_`,
    `TENANT_ID_`,
    `ENGINE_VERSION_`,
    `APP_VERSION_`
)
VALUES
    (
        'withdrawal-approval-id:1:8f67e34e-dada-11ee-a27d-20c19b7a45c3',
        1,
        'http://www.activiti.org/processdef',
        '提现审核流程',
        'withdrawal-approval-id',
        1,
        '8f6216eb-dada-11ee-a27d-20c19b7a45c3',
        'processes/withdrawal-approval.bpmn20.xml',
        'processes/withdrawal-approval.withdrawal-approval-id.png',
        NULL,
        0,
        1,
        1,
        '',
        NULL,
        NULL
    );

-- 部署流程 bpmn xml / 图片 存储表
INSERT INTO `activiti`.`ACT_GE_BYTEARRAY`(`ID_`, `REV_`, `NAME_`, `DEPLOYMENT_ID_`, `BYTES_`, `GENERATED_`) VALUES ('8f6216ec-dada-11ee-a27d-20c19b7a45c3', 1, 'processes/withdrawal-approval.bpmn20.xml', '526ac7a6-dafb-11ee-a2e2-acde48001122', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E0A3C646566696E6974696F6E7320786D6C6E733D22687474703A2F2F7777772E6F6D672E6F72672F737065632F42504D4E2F32303130303532342F4D4F44454C2220786D6C6E733A7873693D22687474703A2F2F7777772E77332E6F72672F323030312F584D4C536368656D612D696E7374616E63652220786D6C6E733A7873643D22687474703A2F2F7777772E77332E6F72672F323030312F584D4C536368656D612220786D6C6E733A61637469766974693D22687474703A2F2F61637469766974692E6F72672F62706D6E2220786D6C6E733A62706D6E64693D22687474703A2F2F7777772E6F6D672E6F72672F737065632F42504D4E2F32303130303532342F44492220786D6C6E733A6F6D6764633D22687474703A2F2F7777772E6F6D672E6F72672F737065632F44442F32303130303532342F44432220786D6C6E733A6F6D6764693D22687474703A2F2F7777772E6F6D672E6F72672F737065632F44442F32303130303532342F44492220747970654C616E67756167653D22687474703A2F2F7777772E77332E6F72672F323030312F584D4C536368656D61222065787072657373696F6E4C616E67756167653D22687474703A2F2F7777772E77332E6F72672F313939392F585061746822207461726765744E616D6573706163653D22687474703A2F2F7777772E61637469766974692E6F72672F70726F63657373646566223E0A20203C70726F636573732069643D227769746864726177616C2D617070726F76616C2D696422206E616D653D22E68F90E78EB0E5AEA1E6A0B8E6B581E7A88B2220697345786563757461626C653D2274727565223E0A202020203C73746172744576656E742069643D227369642D64376661646331392D623863612D346665382D623230622D34343464303032303231616322206E616D653D22E58F91E8B5B7E68F90E78EB0E5AEA1E6A0B8222F3E0A202020203C757365725461736B2069643D227369642D34356566396334622D323935372D343864392D613634312D65373333393732363465313322206E616D653D22E68F90E4BAA4E68F90E78EB0E5AEA1E689B9222061637469766974693A61737369676E65653D22656D706C6F796565223E0A2020202020203C657874656E73696F6E456C656D656E74733E0A20202020202020203C61637469766974693A666F726D50726F70657274792069643D2250726F70657274792031222F3E0A2020202020203C2F657874656E73696F6E456C656D656E74733E0A202020203C2F757365725461736B3E0A202020203C656E644576656E742069643D227369642D37656330316534332D373661322D343331612D626239372D61386132633961323864323322206E616D653D22E68F90E78EB0E5AEA1E6A0B8E7BB93E69D9F222F3E0A202020203C757365725461736B2069643D227369642D63353830626539312D393630332D343761342D383730652D36623230323230663830396622206E616D653D22E7AEA1E79086E59198E5AEA1E689B9222061637469766974693A61737369676E65653D226D616E61676572222F3E0A202020203C73657175656E6365466C6F772069643D227369642D65636363623665302D366563622D343763362D623433642D3932626334383533653634322220736F757263655265663D227369642D64376661646331392D623863612D346665382D623230622D34343464303032303231616322207461726765745265663D227369642D34356566396334622D323935372D343864392D613634312D653733333937323634653133222F3E0A202020203C73657175656E6365466C6F772069643D227369642D66323364313366642D643838632D343334302D383935382D3535623266313831643664662220736F757263655265663D227369642D34356566396334622D323935372D343864392D613634312D65373333393732363465313322207461726765745265663D227369642D63353830626539312D393630332D343761342D383730652D366232303232306638303966223E0A2020202020203C636F6E646974696F6E45787072657373696F6E2F3E0A202020203C2F73657175656E6365466C6F773E0A202020203C73657175656E6365466C6F772069643D227369642D31313966346532322D333937312D346666382D393061302D3262663164326432616462662220736F757263655265663D227369642D63353830626539312D393630332D343761342D383730652D36623230323230663830396622207461726765745265663D227369642D64333866386166372D343938332D346239642D383534342D653463343939353466336135222F3E0A202020203C757365725461736B2069643D227369642D64333866386166372D343938332D346239642D383534342D65346334393935346633613522206E616D653D22E8B4A2E58AA1E5AEA1E689B9222061637469766974693A61737369676E65653D2266696E616E63652D6F666669636572222F3E0A202020203C73657175656E6365466C6F772069643D227369642D63333062656636642D383265382D343861632D626666362D3138353031646664613130372220736F757263655265663D227369642D64333866386166372D343938332D346239642D383534342D65346334393935346633613522207461726765745265663D227369642D37656330316534332D373661322D343331612D626239372D613861326339613238643233222F3E0A20203C2F70726F636573733E0A20203C62706D6E64693A42504D4E4469616772616D2069643D2242504D4E4469616772616D5F7769746864726177616C2D617070726F76616C223E0A202020203C62706D6E64693A42504D4E506C616E652062706D6E456C656D656E743D227769746864726177616C2D617070726F76616C2D6964222069643D2242504D4E506C616E655F7769746864726177616C2D617070726F76616C223E0A2020202020203C62706D6E64693A42504D4E53686170652069643D227369642D33383064323431362D353938382D343763662D613532302D633261333634633633383938222062706D6E456C656D656E743D227369642D64376661646331392D623863612D346665382D623230622D343434643030323032316163223E0A20202020202020203C6F6D6764633A426F756E647320783D222D3130352E302220793D2232312E38313235222077696474683D2233302E3022206865696768743D2233302E30222F3E0A2020202020203C2F62706D6E64693A42504D4E53686170653E0A2020202020203C62706D6E64693A42504D4E53686170652069643D227369642D34333133646639662D303331352D343633612D616430372D626538613735393939626233222062706D6E456C656D656E743D227369642D34356566396334622D323935372D343864392D613634312D653733333937323634653133223E0A20202020202020203C6F6D6764633A426F756E647320783D222D33302E302220793D2231342E33313235222077696474683D2235352E3022206865696768743D2234352E30222F3E0A2020202020203C2F62706D6E64693A42504D4E53686170653E0A2020202020203C62706D6E64693A42504D4E53686170652069643D227369642D65626139363532322D336435382D343166362D396561362D663734353737663163333039222062706D6E456C656D656E743D227369642D37656330316534332D373661322D343331612D626239372D613861326339613238643233223E0A20202020202020203C6F6D6764633A426F756E647320783D223236302E302220793D2232312E38313235222077696474683D2233302E3022206865696768743D2233302E30222F3E0A2020202020203C2F62706D6E64693A42504D4E53686170653E0A2020202020203C62706D6E64693A42504D4E53686170652069643D227369642D34626530366531662D336465612D346530352D383834302D303338353838666361343461222062706D6E456C656D656E743D227369642D63353830626539312D393630332D343761342D383730652D366232303232306638303966223E0A20202020202020203C6F6D6764633A426F756E647320783D2236392E302220793D2231342E33313235222077696474683D2235352E3022206865696768743D2234352E30222F3E0A2020202020203C2F62706D6E64693A42504D4E53686170653E0A2020202020203C62706D6E64693A42504D4E456467652069643D227369642D33646266666364332D336433322D346361302D383632662D323632613631326232333163222062706D6E456C656D656E743D227369642D65636363623665302D366563622D343763362D623433642D393262633438353365363432223E0A20202020202020203C6F6D6764693A776179706F696E7420783D222D37352E302220793D2233362E38313235222F3E0A20202020202020203C6F6D6764693A776179706F696E7420783D222D33302E302220793D2233362E38313235222F3E0A2020202020203C2F62706D6E64693A42504D4E456467653E0A2020202020203C62706D6E64693A42504D4E456467652069643D227369642D65346139666336382D323463312D343465632D613366612D633030323161643262616465222062706D6E456C656D656E743D227369642D66323364313366642D643838632D343334302D383935382D353562326631383164366466223E0A20202020202020203C6F6D6764693A776179706F696E7420783D2232352E302220793D2233362E38313235222F3E0A20202020202020203C6F6D6764693A776179706F696E7420783D2236392E302220793D2233362E38313235222F3E0A2020202020203C2F62706D6E64693A42504D4E456467653E0A2020202020203C62706D6E64693A42504D4E456467652069643D227369642D31643532616635652D643030312D343331382D626364642D316564343462623435363739222062706D6E456C656D656E743D227369642D31313966346532322D333937312D346666382D393061302D326266316432643261646266223E0A20202020202020203C6F6D6764693A776179706F696E7420783D223132342E302220793D2233362E38313235222F3E0A20202020202020203C6F6D6764693A776179706F696E7420783D223137322E352220793D2233362E38313235222F3E0A2020202020203C2F62706D6E64693A42504D4E456467653E0A2020202020203C62706D6E64693A42504D4E53686170652069643D227369642D61343763636139392D393631352D343063322D393937652D343330386639383237393234222062706D6E456C656D656E743D227369642D64333866386166372D343938332D346239642D383534342D653463343939353466336135223E0A20202020202020203C6F6D6764633A426F756E647320783D223137322E352220793D2231342E33313235222077696474683D2235352E3022206865696768743D2234352E30222F3E0A2020202020203C2F62706D6E64693A42504D4E53686170653E0A2020202020203C62706D6E64693A42504D4E456467652069643D22656467652D61346539326435652D636336622D343631332D383232322D373437393363306139646235222062706D6E456C656D656E743D227369642D63333062656636642D383265382D343861632D626666362D313835303164666461313037223E0A20202020202020203C6F6D6764693A776179706F696E7420783D223232372E352220793D2233362E38313235222F3E0A20202020202020203C6F6D6764693A776179706F696E7420783D223236302E302220793D2233362E38313235222F3E0A2020202020203C2F62706D6E64693A42504D4E456467653E0A202020203C2F62706D6E64693A42504D4E506C616E653E0A20203C2F62706D6E64693A42504D4E4469616772616D3E0A3C2F646566696E6974696F6E733E0A, 0);
INSERT INTO `activiti`.`ACT_GE_BYTEARRAY`(`ID_`, `REV_`, `NAME_`, `DEPLOYMENT_ID_`, `BYTES_`, `GENERATED_`) VALUES ('8f6216ed-dada-11ee-a27d-20c19b7a45c3', 1, 'processes/withdrawal-approval.withdrawal-approval-id.png', '526ac7a6-dafb-11ee-a2e2-acde48001122', 0x89504E470D0A1A0A0000000D494844520000026D0000005B080600000021B7B22700002AA349444154785EED7D097855D5B9F68EA080380FBFD66AB5EDB5F5B65A875B950EE2ADBFAD7A2DA248E699CC030961489809A84C1111152109610A6324120824391904549C1041400409F4B9D7DBDEF6F1EFD3E12FADFD19BEFF7BD7D91B4ED6CE21E7E49C2467F8DEE7799F93B3CF1ED6FEF2ADB5DE357C6B1986402010080402814020100804028140201008040281402010080402814020100804028140201008040281402010080402814020100804028140201008040281402010080402814020100804028140201008040281402010080402814020100804028140201008040281402010080402814020100804028140201008040281402010080402814020100804028140201008040281402010080402814020100804028140201008040281402010080402814020100804028140201008041EA1B4B4F4A23163C6DC166CE4745FA2BF8B2030505454748DFEFF0A74666767FF2FFD3D04818FCCCCCC4BF5FF65A0B3B0B0F05BFA7B0804024107444646F6E302E3DF0B0A0A668D1E3DBA893FBF6452B0930BC03FF0E75BFC399F3F1FE7771BA0BFBBA067C0E2EC9B6CF354B6F92AE65EFEFB6FFAFF2708F935BFCB01FEDCC89F39FC8EDFD1DF5BD0FB183F7EFC60FE9F3CC3F97C117FEE66FEB193FF5D30B29DDF691B7332F341FDBD05024198213737F7462E18E631FFC72A28727272CE700B95D2D3D329353595121313838E29292994969646191919949D9D7D962BD8B3E6FBFD89B9982BDB7FD16D21F00FD8BEC399CDCC33B0797E7EFEE9ACAC2CE54FA3468DA2E4E464DBFF2BD0999494A4F202DE01EFC2F9E6B4955FD8B7DEE3CFC4D2D2D2FEBA2D043D0B6E68DEC3B65FCDFCBBF9BF38C3F93D247C0DE517CA61F635958F4CFE863989DFFB2ADD1602812084515C5C7C3967FEB20267CFC1592E1CCEA2C0888C8CA4679F7D36E488F7C2FB41C4414CF03B9FE696EB0AFEBC5EB78DA07B605B3E6CF642515E5EDE69543C717171B6FF45A83026264655AE2E02EE37EC53CFEA7611F81FDC10F836FBDA6653A89D86488B8F8FA7912347DAFE4FA1C0E8E8682540B9C1A0041CFBD95FD1FB26D3400482300067FA5F307F87CCCF22E62C0A04BD900865E27D51C8F3FBA3F7ED2FCC28DD4602CF8179436CC34AF8137AD5D053A0DB3CD48906818B78AB67D17AAD6E27817FC0228D4D5CF035F30C7AD342B5A1E98EB1B1B1183D50E28D6D719479B76E2381401022E08C5E8CC20E150C32BF5E208413D15382A160B3A27D194117BABD0417068B935BB9D238041BA2D729547B3A3C21DE1DBD21E650FC7F63E84EB797A0FB48494919C876AD81AF616420DC1A9B3AD1B3884612042CFB5CBC6E2F814010E4E0CCFD9255E0855BEBF442347BDDC01A9997E439B8C2B8832B8BFFC13CA28484049B5DC395680CA1323587B07EAEDB4DE03DB8917919FBD9DBC8A7E85DD36D1EAE8C8A8A42AF1B841B1A0A85BADD04024190822B8F9928F030B955CFF8C26755E08229DC56B2B92274FB093A62ECD8B1B7B0AD7E0B71821E4BDD9EE14E54A6E8CD8670931E37DF80795BEC6B2DE8C1C430B46EEB70277A7831CDC5146EA9BAFD0402419081337214040932B69EE185E78916BC29DC8A751B0ACE832BCF016CA37DE8610BF721F60B11C2CD1CBEFA6F99E3D67DB0FD5E47BEC4D0B36E63A193106E98E786A00CB6D54F751B0A04822001166BE44CFC7F7372724EFB3ADF68E448544423292E2692E26323D527BEE3B87E6EB01243C76CAF535CF83DA0DB52E084B916969A7CAFDBCF5B46468EA498E891CA9F40FC8D63FA79C14A885AF41031B7EA7614740DF6B367E06BFE1A2108657FB37A77D9D77ECBE5D895BA2D05024110800BBCADCC33C8D07A26F79631D19194961C4D8539F1347E74021564C7515A4A34C5C684CEFC38085B2C57819E242C38ACDB33DC919F9FFF6388107F55A209719194931EA3FC09C4DF89F1A1E34F207A88203CD86E23747B0ADCC35C96E8770816D26DDA5D86BABF61AA821908F38A6E4F814010E028702EEDA1A2FAF4CCED292162D022CDCB88A569E392E9C519A368E9BC4C5AF66296FA7CB1348DA68D4FA2BCCC588A8F0B8DC20F4B56C06E05323FC406B6C92E0CC1F812C8829E5988FFF1F909347B722ABDFA42BAF227F0D51732E885C9295CA1C6AB064128F4E25A0D01B6DB090974F11CD63C5C4449EA36F586E1E66F666015A2E26FD76D2A10B8479571B9516EDC67541831FC99C59C6132CB3C769F3A47D063E04AA20D736A7C1916C5F067516E3C55CECFA4A65505F45EED58DAB365BCE2C7CCF7F97B231FAF28CBA4A2BC788A36874B83BDF0C37032177AEDB20CC879B03F3D844A14411BBABDBC611C3702E64C49A1DAA579B46BC39873FE64F9D4AE8D63A8B63C8FE64C4D39D71008767FC250B2D91048D4ED2AB0837DED0A166D7FC662B2BA2DBD65B8F91B46554CD156A5DB355C71C2306E6D378CC799C9C70DA38439039FF88EE3F85DBF263CB0C4B897C5D81CE647CC334CEA823807E7CE51D70AFC06B4B29867BB5BC142E861D8133D680B67A629C1B67F7B311D7B6B0A1DDF39B5C3278E37AD2EA085B3D2D4F928247D118A81402C61814A962B8E5FEAB60D57B03DD6A097ADBBFF5B5C9792144D13C724D2BA5773E8C3CDE3E8F3E6897462D734E54720FEC631FCB6EEB51C752EAE09E64AD4A2B9F8EE6EDDAE023B38DF6520FFF912E812CEFE66F6B661FDB62B74DB86037E6F18834D41B691C5D9574CF2805FE17C5C87EBF57B860E4A8DFE2CBA9299073A1165DE12F74856F714F804CEB0A528F47C5980326B540C2D98318AB62CCBA73D75E3E95063311D6828A64FB74F38477CC771FC5EC7E761F8343B2D36E80B3D14F8666B75A56EDB7084B921F7DFB10D986E2B4F099F98C495E2AA85D9B473C318E537071B3BFA138863F80DE7ACE473714D54084C16C7340534A46493F9AEC176DA892165DD86DE309CFD0DDBC7A1FC2F08B3291E2CBC6E67D155C99F273B1165E7D87EE9A574BC5F3FDB71179E34EF136243CCE5C6D3CC1396E8BAA8FC22BAAEEA3AFAE5BA5F52CEA61C9A513783CAB797D326C726AA6BAEA3969616AA73D4A9EF95DB2BE9B92DCF51416D01FDC7BAFFA09B96DF4417575C6C8937DCF369FD7102CFC12DAC0F31C4A767664F89026F4C4E3C552FCAA177DF2852E2ECB0A3840E6AA20DDF0F3B26AADF715EF5A26CBE2E8E22835CB4818824E516FF1F74DB8623B8F07F1C95802F8BE8A2222C9B3E8A5AD614D2DEADE3550F072A4BBD123DD458A27EC3393817D74445DAEF176C44AF91599166EBF6159C87198070CAD7609770F7374C8DE17A60836EDF50C451C3B88E055615F3B4125D111174E4B6DB68DFB061B4272B8BDE5DB080DE5ABB96DA366D523AC462ABC3413BAAABE9EDD75EA30F264CA07D4F3E499FDD7DB7ABA0C3FDAA707FFD99C185727E8172638B6B2FD9E0CAC14A7C15D616D2EA86D5E468767430CE85B8BE713D956C2EA167373C4BD72EBBD6B5E70DCF087263F53ED02B82612C5F021020BA4A0A13696BD568DABF0D051B7AD93A1676E001B0C1D95ADDCF7FE37C5C170A61F45640020BB7EFEB360E37B01D66B34F9DF125000115E1EB7332D43CA2CF9A4A3AF527579F3AC4E7A0225D3A3793A2A2ECF70B46A2F788FD699D6E5FC179B07D1E43BE436F916E3F6F18EEFE865E71CEB321DFE86C378C6816567FB47AC98E5E7F3D1D78E8217A6FDA347AABA64609335D73B8E38E0D1B94C0DB1B1D4D476FB881DA2FBAC8126F7FC473F46707072A8D212CA4BEB484D580CA013474CD506A6C69ECF0F2D50DD53471F3441ABA7628DD5875230DAC1CA8CEBFA4E212BABEEA7AFA49F54FA8605381EA71D30D377CC370BABCF2724BB87DA99E29F0189C51EFF5B55704A26B525122395617722B74127DE698682BEC5C894211E739AA0B69F2588836FB3D838D2E3D23CFE8360E37B04F6DC9CDCD3DA5DBC81BA222AC2CCB54BE72B475B2CD87741E6999A47A71AB1664A9F5B4F4FB0523B3B2B2B066DB01DDBE82F360D156847CE7EB3245E1EE6FD62E2F39393957EB360E05EC308CFE2CA6965862AD3D2282F63DF30CB5D5D7DB344577D8B66D1BED2E2D75156EE0123C574F4BE0A2C2F8358BA8931053FD2AFAD1C36B1E56A2ABA9A5895A5A5BE8852D2F50DCC638FAEE8AEFAAA152D79E38778C288FA09B97DF4C23368CA0A97553C9D1E2A0E696665ADDB89A1E5BF79835647A523D5BE011B85218E96B4B153D6D98DBD1B0B240157AA05EC8B912AD541091A4107BA1D0D366466181E3751B871BD806072138741B7943441657CCCF3C5741EA3EA4134356F844C51B1D029528684E10FF9B6E5FC179B07D5EE332EC6C77035E2C86BBBF5911CB585B51B771B0038102270CA3D11253FB9F7882DE9D3FDF26BCFCC11DEBD6D1A7BFFA151DBDF65AF52C3C373802159C82ED1484D6808A0174DFEAFB68D9F665EAA5B63AB652D19B4574C7CA3B6850E5209B30F3841067B7ADB88DB23765536D53ADBAEF9AC635F460F58334B042F5D29D12E1E619B8A59A86CCEA4B1042241798587C72D3D23C35BC80396B1802ED2C10C1797C02EDD9328E3695E7D18482D0106DA0393CFA9C6EE37003DBE177BE0421809863F4CAF3E9B47B5391F29DCEFCC9F2290CC7EFDF365E9D8B75B5B0F48C7EBF60A4198C40B25E9B7BB07DAA59B4F9D4AB0B86BBBF617D3BB3D1F9BF751B07337E6318035938ED50822D22820E0E1942BB962FA7162F8641BDE5AEAA2ADACBF6B444229E8F74E8690B1C388744550F1B985B9B4BDB9AB7A99729AB2FA3BB57DF6D1361BEF07B2BBF47A55B4AD5FDB7376FA7D1B5A3ADDF4ECA5069D7E002AF0099D597F9472399F99971543E2F935AD714D227F5CEDE34BDD04320028EE3774CE22DE7966D7E565CD0478F5AC4DC40B6E54BBA8DC30D6C83BF606F56DD3EDE100D81E726A6505D653E7D503B56F94D67D17CA840F1DB076F8EA3CD7CEE7393524262B81D74D91D212C9762F0046C9F377D09A2B218EEFE664DEFE046E753BA8D83156418112C9A6A9578EAD78FF63FFAA84D60F5243F183B560DC39AE2AD16E9D1D3D8F770061DA8396CE861435428863021A6D2DF48A7CB2A2FB3892E7F103D76293529EA394DCD4DEA59E65029D222C109170067D4429F451B17784909D134B1309156BE944D1F6E1EAFD635FAA26DB21A6AC050023EF11DC7F13BCEC3F9C909BECD45092422028B0BBD85BA8DC30DEC4F7FF555B481580E66CE9454B5EEDF31D3772C7FB27CAADDF4339C8373B3D36242A6116089B63163C65CA5DB58E0040BDACDFE106D6038FB1BB6B4327BDA86EB360E561C772E8CAB44D387FC6E6DB5CE5139B77438E8ADF5EB5564E8275CA71DF8C52F5474E8E11FFC800EFFF08774F0673FA37D4F3F4D1FE6E4A808524F82163ECACCA4A3DFF88625DC4AF434F63DCC2851CC61C390287AD820A4666F9D4D572CBBC226B6FC4908C2395BE7504373036D716C51C3AFC652F5DB163D9982F3F0876803517025B3709B599C429B2BF269F7A6B1B4A76E9C8AB0B288EFD825012DD499C5C94AB0A185EBEB7C9440A1883627FC25DA306495352A96969565D1DB1B8BD4A2A61F6F39EF53F81BC7B04AFDB2B24CB55620AE09157F12D1D635FC29DAC2D9DF424DB4B140BA9F790A6209826B474D8D4D50B9B2B5A1C1B98CC7B06174E4861BE878C780827344CFD9B12BAEA0830F3E481F9494D0CEEA6ADBBD5CB963CD1AFA242ACABA1EE9B95F4F6BDFC1B90E9B1250083AB0E6B0A1D7ABA7059BC56BAAAEA1FCDA7CF5DCB2AD65F4A3D53FB27E9375DCDCC05FA20D440196961C4313C724D1CBB3D269EDAB396A590F0428E013DFB113025612C7FE7EC1BE28A54E116D4EF84BB48188CCC3D03B86AEB0FF23B610823F81F81BC7664D4CE6736243228ACF9522DABA863F451B18AEFE164AA28D0CE32216477B2194BEB8EE3A6A69ECB85A854ECC71B30207BC65FBA041B47BEE5CDB3D5DD9CACF3F72EBADD6357B913E3DCDBD0FE74E076AE15C0C8B566CAB700AA7FAB21E1B127547ACDDB6B261A58A2AC582BCE6F113B27342E7F0A768B3181D1549D96971346342322D284D53137C5F9A9946D3C727A92108446AE9D7840245B439E14FD16631363A92268C4EA479D346297F02E74E4DE563096A2B34FDFC50A088B6AEE16FD16631DCFC2D9444DB09C3488340C2B0E48785853611754E4C6DDB46FB1F7B8CBEB8FA6A9B18F38A11116A38755779B9ED191677AC5D4B5F5C75953A1FE9D3D3DCFB706E4DA504127AD91A9B1B5594A8BF830E3CE5F0F5C395684364A9CB02BCC97AB2053D23DA400C97C670C19710174989CC042EE8F03D14E67FB8A38836277A42B481E8994585097F02E3622243AEB7D69522DABA464F8936309CFC2D54441B19463F1646C7208EB0B4C78E55AB6C024A093687833ECAC9A1638306D9455837F8C51557A8A1D5D64E9E65F1C0CF7F6EADE3760CE9D4D3DEBB30F712C54E076AE1DCD616B5AC872EA67A93AF6C7B45196A7ADD74EB982C52D909FC21DA103D0A4196181F45A949D1949612A3382A395A7D4F49747EE2BBF51BBEE3FC50127222DA9CF08768C35C475498F01DA7DF383F411CB38EBBFE8663B82654969011D1D635FC25DAC2DDDF4245B4B1201A6109A9B7172DB209278BBB67CEB4092F7F100BF6EACFB288B5E13EBBEB2EEBDC117ADA7B0F4B8C7B218AB0402EB6A642E2B070AE0A04E8444CF516B1006F4D538DEA71C35EA5EA38D22AE8007F883608AFACB4189A363E89CA668CA2D766675045591655BD9845CB1764A955C3D5277FC771FC8EF3A68D4F56D157188AD0EF198C14D1E6843F441B7A68C7E6C5D3EC2929B4E8F9745A3A2F53CD27B2FCC9F2291CC36F3867F694541AC7D7A05744BF5F3052445BD7F097680B777F0B15D176C230EA208A0EDF79A7DBE8CEF7264FA6CF6FBBCD26B8FCC5DDB36675FAECD6EDDBE9A38C0C750ED2A9A7BDF7506ECC8120C2E6EFD84B1489C34E07DD5D38D75FBC6BD55D347FAB73D5E39FAEF9A9757C8E9EFC50417373F3B2D6D6D6493B76ECB84CFFED42E8AE6843EB32212E8A7233E268CAD8245A382B9D562DCCA6372BF2D44E07AD6BC7D08EF52637149DFB1BC7F13BCE5BFD72B60A58983A2E9972D36355C119CCADD650126DEC4FF770DE71B04FFD44FFAD2B7457B461FF47F4C016E5C6D3F39352D4BA7F35AFE752FDF2D1D45C5D486FB9FA93E9533886DF704ECD925CB5AAFD0B9352B9024EA0517CAF609E3F194EA28D7DED3DF6B9D8D2D252AF2669FB22DAC4DFCE3390445B77EBB2238671390BA27F42147D387AB44D3459FCF497BF74DDE4DDEFC43CB99D2B56D89E0BBE337FBE75DE3F915EFD1D7A07E5C64710448FAE7D546DFE8E84616B2A5D44F536D1F3175F13AFD293B6294DED636A20AD210A7ECFCDA663FC1F6F1CBEBBA20D439BC58589AAD589C82A2C3689253DB02302C2E33F01EB3BE1B9F079E71220B8AE91AF5F3227838A0B129570D39F152C0C41D16615365E89B7EE8AB6CC5131AAF762C3E25C7AB7A688FDC3E923F015CB773AF3277C5AFE846B702DEE317B722A65A4767FA78FBE669889B63F99BE76D81BF1E68B68137F3BCF40126DDDADCB58083D0941D43E6040A73D5D605B5D1DB55F72894D68F993B8FFC72929B667834897CBF39FD4DFA1E7B1D8B88C85D0698824EC7CE06876A8CDDFB14E9B2EA2FA8243AA872843218A143B2718482BD21C827071748B1E39BCB7A20D3D61106CD8B66AE9DC0C72AC2EA0038D25747CE7D40E8B516233E5CF1CCE7D48CF91BFE3B8B55025CE3FBE632A7DDA50424DAB0BE975166EE3F2132881EF1F8C3D6E212CDA2C7A24DEBC156DD80608F384D0DBF1C6925CB5965FFB8E29CAA72EE84F9DF814AEC102A85827B066499E5ABA01F38F8271ABA130156D163D126FDD116DE26F7606A868F3AA2E6311340F62E8B33BEED0CBAD73FC84DF5517593DC1C3DFFB9EEDD916B168AF79DE3CFD1D7A1E95C6BF5902A9B4CEB99554C99B2536F1D457BC7DE5ED2A4DAF6D7B8D1EA87EC0791C690E41B4D81DDDE2051DDE5BD1161F8B5078166CF332D450A7B5B50BF61305F56D5F2E44EB1AE75E7ECEADAD96CCCB548210CFD19F1DE80C03D166F182E2CD5BD1969E12432F4CE60A74699EEA8545E578C0F40FDD67BAA2F229FEC43DD01B82212C6C358467E8CF0D7486B968B37841F1D61DD126FE6667808B368FEA3216415B21860EFEE427FA758AADCDCD3D3A97CD95C7060FA696A6265B1AC00FF3F2ACF3B6EAEFD0F3586AC45A02A97CBB738D92A16B86DAC4535FF1CACA2B559A36346D504112EA38D21C826871EFE8163B75786F455B466A8C9ABFD6B8AA903ED956AC5A9B28B0F4C2CC1BA2F58AFB604F520CB5624D373C477F76A0338C449BC54EC59BB7A26D4241821A5E426FC5E7EC07F005DD47BCE5D156678FC9BB6F14D1BAD7725443437F6EA053445B07762ADEBA23DAC4DFEC0C12D166B1D3BA8C45D01188A14F9E7A4A3F5F116BA5E9E2AA27F9CE2BCE152C74EE5CBEDC3AE7886BFA7B07E546BE259010A98904DD5075834D3CF515FB95F75369C2B65623378EB48EE7D7D4D45CD2D8D8785B2891DFD3A13B871B7670784F451B96E5888E7A96C6E67381F77A1EEDDBE6DC38592FBC7C217ADC20DC50A062622F16E9D5D311C8C486F1D3A64DABD2FF37C1C8B6B6B6273AF11D77EC20DE3C156DF0A9B89891346FFA287ABF769CAA40759FF085FBB78D57152986BFE64D4B55EB6CE969086442B44D98308156AD5AF523FDFF136A641FFA4B277ED5193B88376F449BF89B7B42B48D1D3B9666CF9E9DA1FF6F7A9B284F3AF9BF77C60E75198BA0AF1060E06E3ED9DB8B17DB84554F724F66A62D0D60DBB66D6A415E3EE72BABCCEC3D5418932C81046184040DAC1868134F7DC588F208B5813CF63F8DD918E33CCE69F6B01721D4A91CBEA4A4A4D813D186A8A874731EC8F615A3B9709AE4F7424FDD9389DE360C5FE079C1148D3565CA94339DD8399CA8C49BA7A20D15E8E8AC58AA9C9F49FBEAC7ABF941BA4FF8420CB963CE24EE5D59964905D9716A5D2D3D1D814A88B6EA2EF6360C632AF1C6A2ADCE53D126FEE69E106D0B172ED46D1C2C547559FBA0415FB75F7C317D9C96A6FFAEF84E59994D58F524F7C6C7DBD2006201DEF6FEFD71CED7BAA4EA79B888362CAAEB6871D884535F13820D3B34C4D5C4398F8968EBC0FAFAFA76B4B0BA126D287CB08E1182055AD714D2C146E7C45CBDE0F285B81F7AEF30B76DF19C742ACA8D0BAAD6AA8836C5AFE7CE9DFB774F445B6A52949AB8BD7171AE8ACAF3C730952B518962D80AD17E1B5FCF557B466259063D1D814A116D5D73F9F2E5C73C156DE26FEE19E4A24DF1DD850BCFB45F7925EDE1B247FF4DFD3E77AE4D58F524DD0DD382C7060EC4397D20DA5C864703B1A7CDDDF028ABF21F307F134AE4F73CA93BC685C8C2F5107F468D6178D2D386A538A68C4DA4EA45D92AC41D05DE176D93D57080BF88FB1DE142EF1DBEFFEA45396AFDB7605A0264D2A449A71B1A1AFEA2FF6F8291EC1BBFD57DA60B7ECD7CDDE170DCE2694F1B965C58342B4DF5DC1E6C28563D1FBA4FF84244F9E19E071B8B69DBF2D1F4F273E9EA997A3A0295106DCB962D836DFF53FFFF841AF91DBD6AF070F9D5D0D6D6F68037C3A3E26FEE09D1565656464D4D4D7FD0FF37BDCD966ED665C72322BEC236511F2725D9CE0131C74C17563D496C93A5A701ECDBE1519740046B4EDB8DCB6FB489A7BEE2A59597AA34BDD1F4063DB5FE29E7F1F00D4450B41C9C8822709DA773DA92E2A3D486EF6B5FCDA15D1BC6A856E5012EF83014E02F7EDAE08C22C5FDF19CE9E393D573F5B4042AC33010013C27D6AC6B3D156DD969B1B47876BAAA4411C587CA4EF7095F881E0FDC13F7C63316CFCE50CFD4D311A89440043B2DB1665DE78D68137F73CF200B44B07CA1435D76DC0A441831C2762EB8B3B2D226AC7A92BB9F7BCE960670C7FAF5D6397D1088E0B2E4C7D26D4B5582B061BC2E9EFA8AB7ACB845A5A9727B253DB4E621E7F1F05BF2435177700B9E8A36EC7E30714C222D999B41B5E579D4B4BA4011BB1BF88BCE7B16F2FDF3F93999FC3CF4B48968EB0B7820DA6C62CD82A7A20D11C2F3A78FA235AFE4D0F695A3C9516DF7095F897B6E5F59A09E51363DB8A29245B47528BF3A88350BDE8836F137F70C26D1E6AE2E3B6E2EF971E0A1876CD780AD4D4DD65CB29E273FA7ADBEDE9606D065DFD33E58F2C36571DD1975335482A6D44DB189A7BE62982FAE7B4107B7E0A9688B8D89A4FCCC589A5992ACBAFD5F67F18635D530C7CD5F5C322F43DD17F79FC5CFC1A4E1609AD31626A2CDAD58B3E0A9684B4E8CA249DC104045BAD8FAFF77E217DDA6F2D10C75EF327EC6E4A2444A490C9E46808836F762CD8237A24DFCCD3D8341B47555971D3717D73DFCDDEFDAAEB5B8FFF1C7ED02AB07888DE1F5675BECDBC57501731BABEC4DD92A10615DE33AEA5FD1DF26A07A9B881C1DBE61B83252416D015DBDEC6A1C0F876DAC3C72700B9E8AB6A8C8916AC15B4CACC53C8D9C8C38CA498FF53FF9BE9969316A55713C0FCFD5D312A80C71D1D6A558B3E0A9684364707242948A12CED6FDC08FC4BDD353A355051A4CD1C8E12CDABA126B16BC116DE26FEE19C8A2CDD3BAECB8B98DD5B1CB2F579BB36BE597E2BBF3E6F5E8BEA3207AF3F60D776A0F1B1D0E3A72E38DD6B97DB08D15606E18FFC8DA476845837393D43B56DD611351BDCD9B97DF4C93374F56E939B7B06E086F18DF623ABAA70E6EC153D126EC9A212ADA3C166B163C156DC20B331C459BA762CD8237A24DE89E8128DABCADCB5C378C7F7FD224BB603279F8873FB4092D7FF2E08F7FEC7661DD5DE7E7D5F5E186F14B8C7B2188D09395B7294F252CB526952EAFBCDC26A47A9323368C50C111CD2DCD74D3F29B9CC791D61005DBFD796F1CDC828836FF3194445B6B6BEB0F5ABC146B1644B4F9876126DAD67923D62C8868F30F034CB475AB2E034E18461D44D1E7DFF90EB53634D844130841D763DB594544D0EE59B3548F9AFE5CF4FEED494F57E7219D7ADA7B17E5C6010C473EBAF65195B885F50BE9DED5F7DA84546F726ADD5425D896352CA38B2B2EC6B1037AB20522DAFCC950126DBE40449B7F184EA2ADBB10D1E61F069268F3052C8846401461E98F5DCEE5726C7CABA686F62624D805971F88B96C6FBDF9A6ED9920D273E0E187AD7347E869EF5D941BC9104A97545C429B1D9B5502676E99691352BD45F4AC612704A4C30C400093F5640B44B4F99322DA9C10D1E61F8A68EB1A22DAFCC350116D6418FD58101D8330C28E046D6EE6B6A117EEB37FFD579BE8F285476FBAC9ED26F1E02751512AAA94CF3D8674EA69EF5D941AFD59149D8038BA6BD55DB4D5B1959A5A9AD4DA68E881D345554FF2DA65D7D2CA8695AA970D51A3E6F1132A8D021B44B4F98F22DA9C10D1E61F8A68EB1A22DAFCC350116DC009C3485322EA861BE8A33CE794AD4EC9C20DD1A45F5C7DB54D8079CBFDC386D1DB4B9DCB9E75C6B7D6AF3FF71CA44F4F73DFA0DC781A02E9A28A8BE8C5FA179568AA6EACA66F2EFFA64D58F51407550EA227D63FA18CB4BA71353DB9FE49EBB7A7F5E40A9C10D1E63F8A687342449B7F28A2AD6B8868F30F4349B491615CC4E2682FE6977D76F7DDD4DAC9FC328B080CD8C3F9ACDDB9AD94F7ECDF9F3E1D3A94766228B6B9D9767F10CFFF98ED6B5EB317E9D3D3DC772837B64024614812BD5C4870597D99EA7DD305564F30A52685B6366F55CF7D6CDD63562FDF163D9982F310D1E63F8A687342449B7F28A2AD6B8868F30F4349B4012C8EEE679E825072B771BB2BDB6A6BE9D0830F7A25DE3EBBF34E7A7FDA34DBBD74BE377DBA750DD273BF9ED6BE45B9711DF34B08282CB951B1BD42257A4DE31AFA59F5CF542F9C2EB4FC41F4B041B0617378CC654B7F23DD0A3E405AAED39329380F116DFEA388362744B4F98722DABA868836FF30D4441BC002A94489A58808FAA0B0D06D80C0393A1C6A8BA9F7274FA64F468EA4038F3C429FDD730F1DBEE30E25D00EFDF8C7B4EF9967684F5616BDFDEAAB17ECC1B388E1D9CF6FBED9126D257A1A030395C610164A278DA586DA3ACADA93B47C7BB98A2885C0D245972FC41CB6C7D73DEE9C47C7826DD1B645D642BA27555A0417848836FF51449B1322DAFC43116D5D43449B7F188AA28D0C238285522D04D3E7B7DF4E1F9494D844953BB66DDE4C3B57ACA077162CA0DD2FBC40EFCE9F4F6F2F5AA47AE43A5BCEA333225AF4F36F7FDBDA1CBE16E9D1D31838A8307ECDA2E91444D5DDABEFA605F50BD44B60C704FC8D8DDC75F1D51D224A1441079691D0C376D5B2ABF0DB2995064197E04C3A5A449B7FC815C819B6E502DDC6E106B6C39F45B4F9CE9494145591B23DAFD06D2C70821B49B522DA7CA725DAD8D786E9360E66FCC630069E308C1DAAA7AB5F3F7A7FC2049BB8EA09EE2A2F57CB8EE0B9783ED2A1A72DF0E0146E2721AEAE5C762525D724D3A6A64DEA85EA9BEB69DC9BE3E8D7EB7F4D3756DD48E895D3055967C41C350CBB62E15CACC30611888007041D600E9B39247A52049BE7E04C3A0A99159956CFC842CF3972E44855E871253253B771B881EDF065464686CD4642EF08E1CBB63CCB0DAA3E5E1E2070C1E5D72AE629DD7642EF181F1FAFCAAFFCFCFC47741B073B7E6F18835938355A73D1F63DF514BDFBD24B36A1E50FBEF5C61B2A22F5E8B5D75A82AD11CFD7D314B8700E95AA396E18168DAF89A797EB5F562F67ED535ABCB99886AD1F460F543FA004197AE1FA55F453220D9F83970DA65B56DC420F563FA8F612C5D654D64E07B8CFF35B9F5751A266D0C1973224EA1DC68C19F334322B32AD9E91859E333A3ADA6AA98ED16D1C6E603BECCFC9C939A3DB48E81D217CB911F057DDBE82F3605F7B19F90E8D26DD7E42CF99949464955F21B96BD00EC3E8CF226A0984D4B1C183E9D07DF7D1BBB3675F705D35AFD8D848BB962FA77D4F3F4DC7CE07332CC173F5B4043E9CC1092AAAD4E290EA212A38C1F6E29EB2B585AA1AAAE8F615B7BBF6C4E1191274E02538A3DE89CC9A989868CBC842CF191B1B6BF5B4857D2F2FDBE18DBCBC3CE9FDF09159595967D9961FEBF6159C07E7B77CE4BBA8A8289BFD849ED31C8A3F5B5C5CDC47FB61F60EDA0D239AC5D41FAD5EB7834386D07BCF3F6FD7185EB075CB163A74FFFDD47EF1C59658FB239EA33F3BF8E05CC74D2DC00B0EA81C40DF5AF12D1AB6611815D616D2F35B9EA7CAED95B4C9B189EA1C75CA18F8C4771CC7122205B5056AF377CC6533874241DC53D661EB26B86535800BBE7FCA1C24DF684D1A67B172AB6EE37003DB61062A009927E91BCDC09615BA7D05E7C1E5D7C3C87709090936FB093D674646C659B6E57FE9F60D451C358CEB585855314F2B91151141476EBD953E193E9C3ECACAA277E6CFA79DD5D5D4B6C9399DEB9C384374E99A352A721411A6E8553B74CF3DD62E0720EE5785FBEBCF0C5E38774E4836B00FA84BCFDB3555D7D0F7577E9F86AE1DAA86409FDDF82C25D524A9B96BF88E2854ACFD6646855AC43D9265A703DFC19975078B0D99CCEB03B3B2B21084F09FBA6DC31156452ABDB7DD675C5C9C15CD27DBEF5D0029292903D9DFFE919E9E6EB3A1D0339AF371CF845B038105D6EDED8651C99F272DF176ECB2CBD4FEA19FFEEA574AC47D9C94447B5252D427D67BDBFFD86374F081079CCB78F4EB6789B593E67D6ED79F115A5862DCCBA26B0EF323E6195711E7863807E7CE51D70AFC062EF426A082906084EE113D4A6C430C652DD66D1B8E282D2DBD842B803F4B3042F76906219CC9CDCDBD51B7AFA023D84EDBD12BA9DB50E819AD2004CEB391BA6DC301081460D195CCDCC8C2EB2B538875C5AF703EAE0BAE40037FA1CAB89CC5D87D468511C39F59CC1926B3CC63F7A973043D82A2A2A26FA282484D4DB5656861D77499C42B413026B80258CAF6382343A4DD237ABED97E4DBA5D057670DE8B41FE9360AAEE118D2BB6DF5FD06BA9DB361C71C2306E6531F63804D971E702BD33F0690ABBC7F1BB7E8D40D0EBE04CBB0995AC4CE8F58E185A4005CB2265AF6ED370467E7EFE5DEC536765AEA4F7B4E6474A508B67C0BC5CB6D7EF2462D97B627405A304CC1775BB0A0482000642BD9179656E8877B41640C5D229BA4DC31DEC4F9BD1832B0D01CF899E490CF5B1DDF61901BD827A60A1C0DCD9450212BC6366662604DB3FB8FCFA866E53814010E0E0426F19849BCC6DF38C102366845F9B6E4B811A76FF17F6A97FCADC36CF89290AE8A1649F1AAADB53E01E9847C9763B825E6F59B3CD335A73D9B8CC9FAADB5320100401C68D1B771D67E2DFA3E093B94817262A86ECEC6CF4889C648678D450F7C1B699848A01437EBA0D851D89685BD88A59AEDB51D03558E8FE1C3DBBE83DD26D2BEC482C066E36380F617859B7A54020081270067E98799A05C91969B1BA27D635325BA9F1BA0D05E7515A5A7A11DBC9811E5C9928EE9EE6DCA233CC0345454583743B0A3C03FBDA14E44B994BE99E6890E7E6E6A2C1F937166E77E8361408044106CECCA9CCB31026D2E36627E6FDA162907D463D436666E6956CAF83102522DCECC46E1AE63CB6DF8D1D3BF616DD7E02AF10C1765C89FC29D1F076A23C47C006E7C5FFC7FC956E3C814010A4E0422F0FBD239CC14FCB44722751E0A107D21CC29AA7DB4CE01E586FCC146E6765A8F43CCD2151F8D497DC08F8BE6E3781F7282D2DEDCFF65C8B7CCA0DACB33262E0247A73CD48F77FB26D9ED1ED261008821C586C9133F749F40284FBEAF6E8213287144E3147EBB612740DB3C7CD81CA14C1099857A3DB395C8806405A5A9A35117C2FF366DD5E029F801EB739CCB39C6FCFA03753FF1F840B215ACD6564B0EBC1579802A31B4B20108408D0FAC71A64A85CD0EB166EC35B28EC5D7AD7DA47CB02BA3E0173DCD89F26B22DD1DA3F83B947E134048F0A14CBC460DEA859892E44E4A36E27817F80B5EED8D67F301B0A6117198FC6367AD7CCF2AB5996F61008C200E664F26CE6EF91F9D1E384F922A1DA7A45C18E8A15733FF0BE5CF0FF990BFEA952B9FA0FE672206F3211D081883F55C184A2808350C3FA61E85D34C51A3E7770057A9F6E1781FFC1B6BE82F922F31F189E47230C3D4FA1D8D30B5FC3BEB5680CB988B5762EC3A275BB08048210474B4B0B044C06177CEFA130C0770C9DB2883B959595A5BE63927E3012A2013D89FC3E18FEB486AD0EF0E75814FABA2D04BE03FEC2F6BD93B984F927F3FB59AE6C4E71C57A3698FD09028DDF011527FCC9EAA9FD3B7335F3A7BA2D043D0FF813E7E5E9CC1356F985FF0FF23DF27F30FB1BCA5F94C356A3C0F4B96666143784FAE9B610043FFE3F84D7D4800993C9E60000000049454E44AE426082, 0);

-- 历史任务实例表
INSERT INTO `activiti`.`act_hi_taskinst` (
    `ID_`,
    `PROC_DEF_ID_`,
    `TASK_DEF_KEY_`,
    `PROC_INST_ID_`,
    `EXECUTION_ID_`,
    `NAME_`,
    `PARENT_TASK_ID_`,
    `DESCRIPTION_`,
    `OWNER_`,
    `ASSIGNEE_`,
    `START_TIME_`,
    `CLAIM_TIME_`,
    `END_TIME_`,
    `DURATION_`,
    `DELETE_REASON_`,
    `PRIORITY_`,
    `DUE_DATE_`,
    `FORM_KEY_`,
    `CATEGORY_`,
    `TENANT_ID_`
)
VALUES
    (
        'fca4b69d-dadb-11ee-a49c-20c19b7a45c3',
        'withdrawal-approval-id:1:8f67e34e-dada-11ee-a27d-20c19b7a45c3',
        'sid-45ef9c4b-2957-48d9-a641-e73397264e13',
        'fc6319b9-dadb-11ee-a49c-20c19b7a45c3',
        'fc68bf0a-dadb-11ee-a49c-20c19b7a45c3',
        '提交提现审批',
        NULL,
        NULL,
        NULL,
        'employee',
        '2024-03-05 18:34:46.642',
        NULL,
        NULL,
        NULL,
        NULL,
        50,
        NULL,
        NULL,
        NULL,
        ''
    );


-- 历史流程实例的节点表，记录所有节点
--（由于当前流程实例刚创建，所以只记录到当前代办节点处。即：{发起提现审核, 提交提现审批}）
INSERT INTO `activiti`.`act_hi_actinst` (
    `ID_`,
    `PROC_DEF_ID_`,
    `PROC_INST_ID_`,
    `EXECUTION_ID_`,
    `ACT_ID_`,
    `TASK_ID_`,
    `CALL_PROC_INST_ID_`,
    `ACT_NAME_`,
    `ACT_TYPE_`,
    `ASSIGNEE_`,
    `START_TIME_`,
    `END_TIME_`,
    `DURATION_`,
    `DELETE_REASON_`,
    `TENANT_ID_`
)
VALUES
    (
        'fc6c1a6b-dadb-11ee-a49c-20c19b7a45c3',
        'withdrawal-approval-id:1:8f67e34e-dada-11ee-a27d-20c19b7a45c3',
        'fc6319b9-dadb-11ee-a49c-20c19b7a45c3',
        'fc68bf0a-dadb-11ee-a49c-20c19b7a45c3',
        'sid-d7fadc19-b8ca-4fe8-b20b-444d002021ac',
        NULL,
        NULL,
        '发起提现审核',
        'startEvent',
        NULL,
        '2024-03-05 18:34:46.270',
        '2024-03-05 18:34:46.318',
        48,
        NULL,
        ''
    );
INSERT INTO `activiti`.`act_hi_actinst` (
    `ID_`,
    `PROC_DEF_ID_`,
    `PROC_INST_ID_`,
    `EXECUTION_ID_`,
    `ACT_ID_`,
    `TASK_ID_`,
    `CALL_PROC_INST_ID_`,
    `ACT_NAME_`,
    `ACT_TYPE_`,
    `ASSIGNEE_`,
    `START_TIME_`,
    `END_TIME_`,
    `DURATION_`,
    `DELETE_REASON_`,
    `TENANT_ID_`
)
VALUES
    (
        'fc7bf8ec-dadb-11ee-a49c-20c19b7a45c3',
        'withdrawal-approval-id:1:8f67e34e-dada-11ee-a27d-20c19b7a45c3',
        'fc6319b9-dadb-11ee-a49c-20c19b7a45c3',
        'fc68bf0a-dadb-11ee-a49c-20c19b7a45c3',
        'sid-45ef9c4b-2957-48d9-a641-e73397264e13',
        'fca4b69d-dadb-11ee-a49c-20c19b7a45c3',
        NULL,
        '提交提现审批',
        'userTask',
        'employee',
        '2024-03-05 18:34:46.375',
        NULL,
        NULL,
        NULL,
        ''
    );

-- 历史流程运行中的用户关系表
INSERT INTO `activiti`.`act_hi_identitylink` ( `ID_`, `GROUP_ID_`, `TYPE_`, `USER_ID_`, `TASK_ID_`, `PROC_INST_ID_` )
VALUES
    ( 'fcac099e-dadb-11ee-a49c-20c19b7a45c3', NULL, 'participant', 'employee', NULL, 'fc6319b9-dadb-11ee-a49c-20c19b7a45c3' );

-- 运行时执行实例表表
INSERT INTO `activiti`.`act_ru_execution` (
    `ID_`,
    `REV_`,
    `PROC_INST_ID_`,
    `BUSINESS_KEY_`,
    `PARENT_ID_`,
    `PROC_DEF_ID_`,
    `SUPER_EXEC_`,
    `ROOT_PROC_INST_ID_`,
    `ACT_ID_`,
    `IS_ACTIVE_`,
    `IS_CONCURRENT_`,
    `IS_SCOPE_`,
    `IS_EVENT_SCOPE_`,
    `IS_MI_ROOT_`,
    `SUSPENSION_STATE_`,
    `CACHED_ENT_STATE_`,
    `TENANT_ID_`,
    `NAME_`,
    `START_TIME_`,
    `START_USER_ID_`,
    `LOCK_TIME_`,
    `IS_COUNT_ENABLED_`,
    `EVT_SUBSCR_COUNT_`,
    `TASK_COUNT_`,
    `JOB_COUNT_`,
    `TIMER_JOB_COUNT_`,
    `SUSP_JOB_COUNT_`,
    `DEADLETTER_JOB_COUNT_`,
    `VAR_COUNT_`,
    `ID_LINK_COUNT_`,
    `APP_VERSION_`
)
VALUES
    (
        'fc6319b9-dadb-11ee-a49c-20c19b7a45c3',
        1,
        'fc6319b9-dadb-11ee-a49c-20c19b7a45c3',
        NULL,
        NULL,
        'withdrawal-approval-id:1:8f67e34e-dada-11ee-a27d-20c19b7a45c3',
        NULL,
        'fc6319b9-dadb-11ee-a49c-20c19b7a45c3',
        NULL,
        1,
        0,
        1,
        0,
        0,
        1,
        NULL,
        '',
        NULL,
        '2024-03-05 18:34:46.211',
        NULL,
        NULL,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        NULL
    );
INSERT INTO `activiti`.`act_ru_execution` (
    `ID_`,
    `REV_`,
    `PROC_INST_ID_`,
    `BUSINESS_KEY_`,
    `PARENT_ID_`,
    `PROC_DEF_ID_`,
    `SUPER_EXEC_`,
    `ROOT_PROC_INST_ID_`,
    `ACT_ID_`,
    `IS_ACTIVE_`,
    `IS_CONCURRENT_`,
    `IS_SCOPE_`,
    `IS_EVENT_SCOPE_`,
    `IS_MI_ROOT_`,
    `SUSPENSION_STATE_`,
    `CACHED_ENT_STATE_`,
    `TENANT_ID_`,
    `NAME_`,
    `START_TIME_`,
    `START_USER_ID_`,
    `LOCK_TIME_`,
    `IS_COUNT_ENABLED_`,
    `EVT_SUBSCR_COUNT_`,
    `TASK_COUNT_`,
    `JOB_COUNT_`,
    `TIMER_JOB_COUNT_`,
    `SUSP_JOB_COUNT_`,
    `DEADLETTER_JOB_COUNT_`,
    `VAR_COUNT_`,
    `ID_LINK_COUNT_`,
    `APP_VERSION_`
)
VALUES
    (
        'fc68bf0a-dadb-11ee-a49c-20c19b7a45c3',
        1,
        'fc6319b9-dadb-11ee-a49c-20c19b7a45c3',
        NULL,
        'fc6319b9-dadb-11ee-a49c-20c19b7a45c3',
        'withdrawal-approval-id:1:8f67e34e-dada-11ee-a27d-20c19b7a45c3',
        NULL,
        'fc6319b9-dadb-11ee-a49c-20c19b7a45c3',
        'sid-45ef9c4b-2957-48d9-a641-e73397264e13',
        1,
        0,
        0,
        0,
        0,
        1,
        NULL,
        '',
        NULL,
        '2024-03-05 18:34:46.248',
        NULL,
        NULL,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        NULL
    );

-- 运行时任务节点表
INSERT INTO `activiti`.`act_ru_task` (
    `ID_`,
    `REV_`,
    `EXECUTION_ID_`,
    `PROC_INST_ID_`,
    `PROC_DEF_ID_`,
    `NAME_`,
    `BUSINESS_KEY_`,
    `PARENT_TASK_ID_`,
    `DESCRIPTION_`,
    `TASK_DEF_KEY_`,
    `OWNER_`,
    `ASSIGNEE_`,
    `DELEGATION_`,
    `PRIORITY_`,
    `CREATE_TIME_`,
    `DUE_DATE_`,
    `CATEGORY_`,
    `SUSPENSION_STATE_`,
    `TENANT_ID_`,
    `FORM_KEY_`,
    `CLAIM_TIME_`,
    `APP_VERSION_`
)
VALUES
    (
        'fca4b69d-dadb-11ee-a49c-20c19b7a45c3',
        1,
        'fc68bf0a-dadb-11ee-a49c-20c19b7a45c3',
        'fc6319b9-dadb-11ee-a49c-20c19b7a45c3',
        'withdrawal-approval-id:1:8f67e34e-dada-11ee-a27d-20c19b7a45c3',
        '提交提现审批',
        NULL,
        NULL,
        NULL,
        'sid-45ef9c4b-2957-48d9-a641-e73397264e13',
        NULL,
        'employee',
        NULL,
        50,
        '2024-03-05 18:34:46.376',
        NULL,
        NULL,
        1,
        '',
        NULL,
        NULL,
        NULL
    );


-- 运行时流程人员表，任务参与者数据表，主要存储当前节点参与者的信息。
INSERT INTO `activiti`.`act_ru_identitylink` ( `ID_`, `REV_`, `GROUP_ID_`, `TYPE_`, `USER_ID_`, `TASK_ID_`, `PROC_INST_ID_`, `PROC_DEF_ID_` )
VALUES
    ( 'fcac099e-dadb-11ee-a49c-20c19b7a45c3', 1, NULL, 'participant', 'employee', NULL, 'fc6319b9-dadb-11ee-a49c-20c19b7a45c3', NULL );