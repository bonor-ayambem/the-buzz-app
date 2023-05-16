// ignore_for_file: file_names
class NewPost {
  final String title;
  final String message;

  NewPost(this.title, this.message);

  NewPost.fromJson(Map<String, dynamic> json)
      : title = json['title'],
        message = json['message'];

  Map<String, dynamic> toJson() => {
        'title': title,
        'message': message,
      };
}
