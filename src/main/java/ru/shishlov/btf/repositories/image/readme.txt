We have to have an ability to switch type of saving. So as we always have input like
MultipartFile and output like list of bytes, and so we have to control saving and converting.
Entity can be saving in database and in file system or only in database. So we need control
the way we save it and the way we convert in to output. These repos actually will control
only saving (BD - the whole in database, FS - separately, base and file system)
Each of them will have jpa inside. The convertor in **.components will control converting.