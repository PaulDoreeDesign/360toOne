from django.core.exceptions import ValidationError
from django.test import TestCase

from x360.models import RegionModel


class TestRegionModel(TestCase):
    def test_string_representation(self):
        """String representation of region is its name and code"""
        name = 'Name'
        code = 'nm'
        region = RegionModel.objects.create(name=name, code=code)
        representation = '{0} ({1})'.format(name, code.lower())
        self.assertEqual(representation, str(region),
                         msg='Name nad code should be string representation of region object')

    def test_create_region_without_code(self):
        """Each region must have code"""
        with self.assertRaises(ValidationError, msg='Creation of region without code must raise ValidationError'):
            RegionModel.objects.create(name='Name')

    def test_create_region_without_name(self):
        """Each region must have code"""
        with self.assertRaises(ValidationError, msg='Creation of region without name must raise ValidationError'):
            RegionModel.objects.create(code='nm')

    def test_code_value_is_always_in_lowercase(self):
        """Region code must always be save in lowercase"""
        self.create_region_and_check_for_lowercase_in_code('Pl')
        self.create_region_and_check_for_lowercase_in_code('uS')
        self.create_region_and_check_for_lowercase_in_code('RU')
        self.create_region_and_check_for_lowercase_in_code('gb')

    def create_region_and_check_for_lowercase_in_code(self, code):
        region = RegionModel.objects.create(name='Region {0}'.format(code), code=code)
        self.assertEqual(code.lower(), region.code, 'Code region is not lowercase')

