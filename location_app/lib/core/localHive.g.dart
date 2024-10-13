// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'localHive.dart';

// **************************************************************************
// TypeAdapterGenerator
// **************************************************************************

class StoreLocationAdapter extends TypeAdapter<StoreLocation> {
  @override
  final int typeId = 0;

  @override
  StoreLocation read(BinaryReader reader) {
    final numOfFields = reader.readByte();
    final fields = <int, dynamic>{
      for (int i = 0; i < numOfFields; i++) reader.readByte(): reader.read(),
    };
    return StoreLocation(
      carKind: fields[0] as String,
      ccKind: fields[1] as int,
      route: fields[2] as String,
      gasCost: fields[3] as double,
      totalDistance: fields[4] as double,
    );
  }

  @override
  void write(BinaryWriter writer, StoreLocation obj) {
    writer
      ..writeByte(5)
      ..writeByte(0)
      ..write(obj.carKind)
      ..writeByte(1)
      ..write(obj.ccKind)
      ..writeByte(2)
      ..write(obj.route)
      ..writeByte(3)
      ..write(obj.gasCost)
      ..writeByte(4)
      ..write(obj.totalDistance);
  }

  @override
  int get hashCode => typeId.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is StoreLocationAdapter &&
          runtimeType == other.runtimeType &&
          typeId == other.typeId;
}
